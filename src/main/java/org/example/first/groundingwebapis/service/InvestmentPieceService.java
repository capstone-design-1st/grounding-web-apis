package org.example.first.groundingwebapis.service;

import org.example.first.groundingwebapis.dto.*;
import org.example.first.groundingwebapis.entity.*;
import org.example.first.groundingwebapis.exception.AlreadyPiecedException;
import org.example.first.groundingwebapis.exception.CannotDeleteException;
import org.example.first.groundingwebapis.repository.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class InvestmentPieceService {   


    private final PieceInvestmentRepository pieceInvestmentRepository;
    private final UserRepository userRepository;
    private final PieceInvestmentInfoRepository pieceInvestmentInfoRepository;
    private final FaqPieceInvestmentInfoRepository faqPieceInvestmentInfoRepository;
    private final OrderRepository orderRepository;
    private final OrderPieceRepository orderPieceRepository;
    private final NotificationRepository notificationRepository;
    private final AssetFilesRepository assetFilesRepository;
    private final DisclosureRepository disclosureRepository;
    @Transactional
    public void setInvestmentPiece(InvestmentPieceRequest request){
        var findByLocate = pieceInvestmentRepository.findByLocate(request.getLocation());
        if(findByLocate != null){
            throw new AlreadyPiecedException("이미 등록된 조각투자 입니다");
        }
        String dateString = request.getBuilding_date();
        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDateTime dateTime = date.atStartOfDay();

        if(request.getType().equals("ESTATES")){
            pieceInvestmentRepository.save(
                    new PieceInvestment(
                            request.getType(), request.getLocation(), request.getPrice(), request.getInfo(), request.getFloors()
                            ,request.getUse_area(), request.getMain_use(), request.getLand_area(), request.getTotal_area()
                            ,request.getBuilding_to_rand_ratio(), request.getFloor_area_ratio(), dateTime, request.isAutomatic_close_flag()
                            ,request.getPricePerUnit(), request.getAssetType(), request.getEntryStatus(), request.getDesiredPrice(), request.getPiece_count(), request.getLeaseStartDate(),request.getLeaseEndDate(), request.getAssetImage() ,1L /* TODO UserId 변경해야함 */
                    )
            );
        }else{
            pieceInvestmentRepository.save(
                    new PieceInvestment(
                            request.getType(), request.getLocation(), request.getPrice(), request.getInfo(), request.getFloors()
                            ,request.getUse_area(), request.getMain_use(), request.getLand_area(), request.getTotal_area()
                            ,request.getBuilding_to_rand_ratio(), request.getFloor_area_ratio(), dateTime, request.isAutomatic_close_flag()
                            ,request.getAssetType(), request.getEntryStatus(), request.getLandClassification()
                            ,request.getRecommendedUse(), request.getDesiredPrice(),request.getPricePerUnit(),request.getAdditionalNotes(), request.getLandImageRegistration(), request.getPiece_count(), request.getLeaseStartDate(),request.getLeaseEndDate(), request.getAssetImage(), 1L /* TODO UserId 변경해야함 */
                    )
            );
        }
    }

    @Transactional
    public void setFiles(Long pieceInvestmentId, String documentType, MultipartFile file){
        try {
            String resourcesPath = new File("src/main/resources").getAbsolutePath();
            Path path = Paths.get(resourcesPath + "/uploaded-files/" + file.getOriginalFilename());
            Files.createDirectories(path.getParent());
            file.transferTo(path);
            assetFilesRepository.save(new AssetFiles(1L, pieceInvestmentId, documentType, file.getName()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(readOnly = true)
    public InvestmentPieceListResponse getListedList(){
        var pieceInvestments = pieceInvestmentRepository.findAll();

        var lands = pieceInvestments.stream()
                .filter(piece -> "LAND".equals(piece.getAssetType()))
                .map(this::convertToSubDto)
                .collect(Collectors.toList());

        var estates = pieceInvestments.stream()
                .filter(piece -> "ESTATE".equals(piece.getAssetType()))
                .map(this::convertToSubDto)
                .collect(Collectors.toList());

        return new InvestmentPieceListResponse(estates, lands);
    }

    @Transactional
    public void deleteListedList(InvestmentPieceListDeleteRequest request, String id){
        var pieceInvestment = pieceInvestmentRepository.findById(Long.parseLong(id));
        var userInfo = userRepository.findById(pieceInvestment.get().getUserId());
        if(!userInfo.get().getPassword().equals(request.getPw())){
            throw new CannotDeleteException("삭제 불가능한 조각판매 입니다.");
        }
        pieceInvestmentRepository.delete(pieceInvestment.get());
    }

    @Transactional(readOnly = true)
    public NotificationDetailResponse getNotificationDetail(String orderPieceId){
        var orderPiece = orderPieceRepository.findById(Long.parseLong(orderPieceId)).get();
        var order = orderRepository.findByTypeAndId("구매", orderPiece.getOrderId());
        var user = userRepository.findById(Long.parseLong(order.getUserId())).get();
        var notification = notificationRepository.findByOrderPieceId(Long.parseLong(orderPieceId));
        return new NotificationDetailResponse(new NotificationDetailSubResponse(Long.parseLong(orderPieceId), user.getName(), order.getType(),
                user.getName() + "님이 " + order.getCount()+ "개 구매했습니다.", order.getCount(), LocalDateTime.now(), Integer.parseInt(order.getTotalPrice())));
    }
    @Transactional(readOnly = true)
    public NotificationResponse getNotification(){
        var orderPieces = orderPieceRepository.findAll();
        List<NotificationSubResponse> landResponse = new ArrayList<>();
        List<NotificationSubResponse> estateResponse = new ArrayList<>();
        orderPieces.forEach(e -> {
            NotificationSubResponse sub = new NotificationSubResponse();
            var notification = notificationRepository.findByOrderPieceId(e.getOrderPieceId());
            var order = orderRepository.findById(e.getOrderId()).get();
            var pieceInvest = pieceInvestmentRepository.findById(Long.parseLong(e.getPieceInvestmentId().toString())).get();
            sub.setCount(order.getCount());
            sub.setMessage(notification.getMessage());
            sub.setType(order.getType());
            sub.setTotal_price(Integer.parseInt(order.getTotalPrice()));
            sub.setOrder_piece_id(e.getOrderPieceId());
            if(pieceInvest.getAssetType().equals("LAND")){
                landResponse.add(sub);
            }else{
                estateResponse.add(sub);
            }
        });

        return new NotificationResponse(estateResponse, landResponse);
    }

    @Transactional(readOnly = true)
    public List<DisclosureResponse> getDisclosureList(Long pieceInvestmentId){
        List<Disclosure> lists = disclosureRepository.findByPieceInvestmentId(pieceInvestmentId);
        return lists.stream()
                .map(disclosure -> {
                    return new DisclosureResponse(disclosure.getId(), disclosure.getPieceInvestmentId(), disclosure.getAssetAddress(), disclosure.getAssetName()
                            ,disclosure.getDisclosureTitle(), disclosure.getDisclosureContent(), disclosure.getFileName(), disclosure.getDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")).toString());
                })
                .toList();
    }

    @Transactional
    public void setDisclosure(Long pieceInvestmentId, String assetAddress, String assetName, String disclosureTitle, String disclosureContent,MultipartFile file) throws IOException {
        var fileName = "";
        if(!file.isEmpty()){
            String resourcesPath = new File("src/main/resources/uploaded-files").getAbsolutePath();
            Path path = Paths.get(resourcesPath + "/disclosure/" + file.getOriginalFilename());
            Files.createDirectories(path.getParent());
            file.transferTo(path);
            fileName = file.getOriginalFilename();
        }
        disclosureRepository.save(new Disclosure(pieceInvestmentId, assetAddress, assetName, disclosureTitle, disclosureContent, fileName));
    }

    @Transactional
    public void updateDisclosure(Long disclosureId, Long pieceInvestmentId, String assetAddress, String assetName, String disclosureTitle, String disclosureContent,MultipartFile file) throws IOException {
        var disclosure = disclosureRepository.findById(disclosureId).get();
        var fileName = "";
        if(!file.isEmpty()){
            String resourcesPath = new File("src/main/resources/uploaded-files").getAbsolutePath();
            Path path = Paths.get(resourcesPath + "/disclosure/" + file.getOriginalFilename());
            Files.createDirectories(path.getParent());
            file.transferTo(path);
            fileName = file.getOriginalFilename();
        }

        disclosure.updateDisclosure(pieceInvestmentId, assetAddress, assetName, disclosureTitle, disclosureContent, fileName);
    }

    @Transactional
    public void deleteDisclosure(Long id){
        var disclosure = disclosureRepository.findById(id).get();
        disclosureRepository.delete(disclosure);
    }

    @Transactional(readOnly = true)
    public List<PieceInvestmentInfoResponse> getPieceInvestmentInfo(){
        List<PieceInvestmentInfo> pieceInvestmentInfos = pieceInvestmentInfoRepository.findAll();
        return pieceInvestmentInfos.stream()
                .map(info -> {
                    return new PieceInvestmentInfoResponse(info.getPieceInvestmentInfoTitle(), info.getPieceInvestmentInfoContent());
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public InvestmentPieceListResponse getApprovedPieceInvestmentInfoResponse(){
        var pieceInvestments = pieceInvestmentRepository.findApprovedInfos();

        var lands = pieceInvestments.stream()
                .filter(piece -> "LAND".equals(piece.getAssetType()))
                .map(this::convertToSubDto)
                .collect(Collectors.toList());

        var estates = pieceInvestments.stream()
                .filter(piece -> "ESTATE".equals(piece.getAssetType()))
                .map(this::convertToSubDto)
                .collect(Collectors.toList());

        return new InvestmentPieceListResponse(estates, lands);
    }

    @Transactional(readOnly = true)
    public List<FaqPieceInvestmentInfoResponse> getFaqPieceInvestmentInfo(){
        List<FaqPieceInvestmentInfo> faqPieceInvestmentInfos = faqPieceInvestmentInfoRepository.findAll();
        return faqPieceInvestmentInfos.stream()
                .map(question -> {
                    return new FaqPieceInvestmentInfoResponse(question.getFaqPieceInvestmentInfoTitle(), question.getFaqPieceInvestmentInfoContent());
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public InvestmentPieceResponse getAssetInfo(Long id){
        var pieceInvestments = pieceInvestmentRepository.findById(id).get();

        // 토지 건물 구분
        if(pieceInvestments.getAssetType().equals("LAND")){
            return new InvestmentPieceResponse(null, convertToSubDto(pieceInvestments));
        }else{
            return new InvestmentPieceResponse(convertToSubDto(pieceInvestments), null);
        }
    }


    private InvestmentPieceListSubResponse convertToSubDto(PieceInvestment pieceInvestment) {
        InvestmentPieceListSubResponse subDto = new InvestmentPieceListSubResponse();
        subDto.setInvestedPieceId(pieceInvestment.getPieceInvestmentId().toString());
        subDto.setSalesCompleted(pieceInvestment.isSaleCompleted());
        subDto.setName(pieceInvestment.getName());
        return subDto;
    }
}
