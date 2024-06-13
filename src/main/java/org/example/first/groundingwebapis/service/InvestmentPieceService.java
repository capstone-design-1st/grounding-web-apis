package org.example.first.groundingwebapis.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.example.first.groundingwebapis.dto.*;
import org.example.first.groundingwebapis.entity.*;
import org.example.first.groundingwebapis.exception.AlreadyPiecedException;
import org.example.first.groundingwebapis.exception.CannotDeleteException;
import org.example.first.groundingwebapis.repository.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class InvestmentPieceService {

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${spring.cloud.aws.cloudfront.distribution-domain}")
    private String cloudFrontUrl;

    private final S3Client s3Client;
    private final PieceInvestmentRepository pieceInvestmentRepository;
    private final UserRepository userRepository;
    private final PieceInvestmentInfoRepository pieceInvestmentInfoRepository;
    private final FaqPieceInvestmentInfoRepository faqPieceInvestmentInfoRepository;
    private final OrderRepository orderRepository;
    private final OrderPieceRepository orderPieceRepository;
    private final NotificationRepository notificationRepository;
    private final AssetFilesRepository assetFilesRepository;
    private final DisclosureRepository disclosureRepository;
    private final NewsRepository newsRepository;

    @Transactional
    public Long setInvestmentPiece(InvestmentPieceRequest request, Long userId){
        var findByLocate = pieceInvestmentRepository.findByLocate(request.getLocation());
        if(findByLocate != null){
            throw new AlreadyPiecedException("이미 등록된 조각투자 입니다");
        }
        /*String dateString = request.getBuilding_date();
        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDateTime dateTime = date.atStartOfDay();
        */
        if(request.getType().equals("ESTATES")){
            var res = pieceInvestmentRepository.save(
                    new PieceInvestment(
                            request.getType(), request.getLocation(), request.getPrice(), request.getInfo(), request.getFloors()
                            ,request.getUse_area(), request.getMain_use(), request.getLand_area(), request.getTotal_area()
                            ,request.getBuilding_to_rand_ratio(), request.getFloor_area_ratio(), request.getBuilding_date(), request.isAutomatic_close_flag()
                            ,request.getPricePerUnit(), request.getOneline(), request.getAssetType(), request.getEntryStatus(), request.getDesiredPrice(), request.getPiece_count(), request.getLeaseStartDate(),request.getLeaseEndDate(), request.getAssetImage() , request.getWalletAddress(), request.getAssetCertificateUrl(), request.getAssetName(), userId
                    )
            );
            return res.getPieceInvestmentId();
        }else{
            var res = pieceInvestmentRepository.save(
                    new PieceInvestment(
                            request.getType(), request.getLocation(), request.getPrice(), request.getInfo(), request.getFloors()
                            ,request.getUse_area(), request.getMain_use(), request.getLand_area(), request.getTotal_area()
                            ,request.getBuilding_to_rand_ratio(), request.getFloor_area_ratio(), request.getBuilding_date(), request.isAutomatic_close_flag()
                            ,request.getAssetType(), request.getEntryStatus(), request.getLandClassification()
                            ,request.getRecommendedUse(), request.getDesiredPrice(),request.getPricePerUnit(),request.getOneline(), request.getLandImageRegistration(), request.getPiece_count(), request.getLeaseStartDate(),request.getLeaseEndDate(), request.getAssetImage(), request.getWalletAddress(), request.getAssetCertificateUrl(), request.getAssetName(), userId
                    )
            );
            return res.getPieceInvestmentId();
        }

    }

    @Transactional
    public Map<String, String> setFiles(Long pieceInvestmentId, MultipartFile file, MultipartFile[] files, Long userId) throws IOException {
        Map<String, String> result = new HashMap<>();
        String pdfUrlInfo = awsFileTransfer(file);
        result.put("PDF", pdfUrlInfo);
        assetFilesRepository.save(new AssetFiles(userId, pieceInvestmentId, "PDF", pdfUrlInfo));

        int i = 0;
        for (MultipartFile image : files) {
            String imageUrlInfo = awsFileTransfer(image);
            assetFilesRepository.save(new AssetFiles(userId, pieceInvestmentId, "IMAGE" + String.valueOf(i), imageUrlInfo));
            result.put("IMAGE" + String.valueOf(i), imageUrlInfo);
            i++;
        }
        return result;
    }

    private String awsFileTransfer(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        s3Client.putObject(PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(fileName)
                        .build(),
                software.amazon.awssdk.core.sync.RequestBody.fromBytes(file.getBytes()));
        return cloudFrontUrl + "/" + fileName;
    }

    @Transactional(readOnly = true)
    public InvestmentPieceListResponse getListedList(){
        var pieceInvestmentsAll = pieceInvestmentRepository.findAll();
        List<PieceInvestment> pieceInvestments = new ArrayList<>();
        for(PieceInvestment p : pieceInvestmentsAll){
            List<AssetFiles> pdfs = assetFilesRepository.findByPieceInvestmentIdAndDocumentType(p.getPieceInvestmentId(), "PDF");
            for(AssetFiles pdf : pdfs)
                if(pdf.getAdminYn().equals("Y")){
                    pieceInvestments.add(p);
                }
        }

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

//    @Transactional(readOnly = true)
//    public NotificationDetailResponse getNotificationDetail(String orderPieceId){
//        var orderPiece = orderPieceRepository.findById(Long.parseLong(orderPieceId)).get();
//        var order = orderRepository.findByTypeAndId("구매", orderPiece.getOrderId());
//        var user = userRepository.findById(Long.parseLong(order.getUserId())).get();
//        var notification = notificationRepository.findByOrderPieceId(Long.parseLong(orderPieceId));
//        return new NotificationDetailResponse(new NotificationDetailSubResponse(Long.parseLong(orderPieceId), user.getName(), order.getType(),
//                user.getName() + "님이 " + order.getCount()+ "개 구매했습니다.", order.getCount(), LocalDateTime.now(), Integer.parseInt(order.getTotalPrice())));
//    }
//    @Transactional(readOnly = true)
//    public NotificationResponse getNotification(){
//        var orderPieces = orderPieceRepository.findAll();
//        List<NotificationSubResponse> landResponse = new ArrayList<>();
//        List<NotificationSubResponse> estateResponse = new ArrayList<>();
//        orderPieces.forEach(e -> {
//            NotificationSubResponse sub = new NotificationSubResponse();
//            var notification = notificationRepository.findByOrderPieceId(e.getOrderPieceId());
//            var order = orderRepository.findById(e.getOrderId()).get();
//            var pieceInvest = pieceInvestmentRepository.findById(Long.parseLong(e.getPieceInvestmentId().toString())).get();
//            sub.setCount(order.getCount());
//            //sub.setMessage(notification.getMessage());
//
//            sub.setType(order.getType());
//            sub.setTotal_price(Integer.parseInt(order.getTotalPrice()));
//            sub.setOrder_piece_id(e.getOrderPieceId());
//            if(pieceInvest.getAssetType().equals("LAND")){
//                landResponse.add(sub);
//            }else{
//                estateResponse.add(sub);
//            }
//        });
//
//        return new NotificationResponse(estateResponse, landResponse);
//    }





    @Transactional(readOnly = true)
    public List<NewsDto> getNewsList(Long pieceInvestmentId){
        List<News> newsList = newsRepository.findByPieceInvestmentId(pieceInvestmentId);
        return newsList.stream()
                .map(news -> new NewsDto(
                        news.getTitle(),
                        news.getReportedAt().format(String.valueOf(DateTimeFormatter.ofPattern("yyyyMMdd"))).toString(),
                        news.getPublisher()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void setNews(Long pieceInvestmentId, String title, String publisher, String reportedAt) {
        newsRepository.save(new News(pieceInvestmentId, title, publisher, reportedAt));
    }

    @Transactional(readOnly = true)
    public List<DisclosureResponse> getDisclosureList(Long pieceInvestmentId){
        List<Disclosure> lists = disclosureRepository.findByPieceInvestmentId(pieceInvestmentId);
        return lists.stream()
                .map(disclosure -> {
                    return new DisclosureResponse(disclosure.getId(), disclosure.getPieceInvestmentId(), disclosure.getAssetAddress(), disclosure.getAssetName()
                            ,disclosure.getDisclosureTitle(), disclosure.getDisclosureContent(), disclosure.getDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")).toString());
                })
                .toList();
    }

    @Transactional
    public void setDisclosure(Long pieceInvestmentId, String assetAddress, String assetName, String disclosureTitle, String disclosureContent) throws IOException {
        disclosureRepository.save(new Disclosure(pieceInvestmentId, assetAddress, assetName, disclosureTitle, disclosureContent));
    }

    @Transactional
    public void updateDisclosure(Long disclosureId, Long pieceInvestmentId, String assetAddress, String assetName, String disclosureTitle, String disclosureContent) throws IOException {
        var disclosure = disclosureRepository.findById(disclosureId).get();
        disclosure.updateDisclosure(pieceInvestmentId, assetAddress, assetName, disclosureTitle, disclosureContent);
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
        var pieceInvestmentsAll = pieceInvestmentRepository.findApprovedInfos();
        List<PieceInvestment> pieceInvestments = new ArrayList<>();
        for(PieceInvestment p : pieceInvestmentsAll){
            List<AssetFiles> pdfs = assetFilesRepository.findByPieceInvestmentIdAndDocumentType(p.getPieceInvestmentId(), "PDF");
            for(AssetFiles pdf : pdfs)
                if(pdf.getAdminYn().equals("Y")){
                    pieceInvestments.add(p);
                }
        }

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
        //subDto.setSalesCompleted(pieceInvestment.isSaleCompleted());
        subDto.setAssetName(pieceInvestment.getAssetName());
        return subDto;
    }

    public Page<NotificationDto.GetResponse> getNotifications(Long pieceInvestmentId, Pageable pageable) {

        Page<NotificationDto.GetResponse> notifications = notificationRepository.readByPieceInvestmentId(pieceInvestmentId, pageable);

        return notifications;
    }
}
