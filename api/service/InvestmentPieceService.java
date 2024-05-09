package com.grounding.api.service;

import com.grounding.api.controller.dto.InvestmentPieceListDeleteRequest;
import com.grounding.api.controller.dto.InvestmentPieceListResponse;
import com.grounding.api.controller.dto.InvestmentPieceListSubResponse;
import com.grounding.api.controller.dto.InvestmentPieceRequest;
import com.grounding.api.controller.dto.MainListResponse;
import com.grounding.api.controller.dto.MainResponse;
import com.grounding.api.controller.dto.MainSellerDetailResponse;
import com.grounding.api.controller.dto.MainSellerResponse;
import com.grounding.api.controller.dto.MainSubResponse;
import com.grounding.api.controller.dto.NotificationDetailResponse;
import com.grounding.api.controller.dto.NotificationDetailSubResponse;
import com.grounding.api.controller.dto.NotificationResponse;
import com.grounding.api.controller.dto.NotificationSubResponse;
import com.grounding.api.entity.InvestmentStatus;
import com.grounding.api.entity.PieceInvestment;
import com.grounding.api.exception.AlreadyPiecedException;
import com.grounding.api.exception.CannotDeleteException;
import com.grounding.api.repository.InvestmentStatusRepository;
import com.grounding.api.repository.NotificationRepository;
import com.grounding.api.repository.OrderPieceRepository;
import com.grounding.api.repository.OrderRepository;
import com.grounding.api.repository.PieceInvestmentRepository;
import com.grounding.api.repository.UserRepository;
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
    private final OrderRepository orderRepository;
    private final OrderPieceRepository orderPieceRepository;
    private final NotificationRepository notificationRepository;
    @Transactional
    public void setInvestmentPiece(InvestmentPieceRequest request){
        var findByLocate = pieceInvestmentRepository.findByLocate(request.getLocation());
        if(findByLocate != null){
            throw new AlreadyPiecedException("이미 등록된 조각투자 입니다");
        }
        String dateString = request.getBuilding_date();
        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDateTime dateTime = date.atStartOfDay();
        pieceInvestmentRepository.save(
            new PieceInvestment(
                request.getType(), request.getLocation(), request.getPrice(), request.getInfo(), request.getFloors()
                ,request.getUse_area(), request.getMain_use(), request.getLand_area(), request.getTotal_area()
                ,request.getBuilding_to_rand_ratio(), request.getFloor_area_ratio(), dateTime, request.isAutomatic_close_flag()
                ,request.getPiece_count(), 1L // TODO UserId 변경해야함
            )
        );
    }

    @Transactional
    public void setFiles(String documentType, MultipartFile file){
        try {
            String resourcesPath = new File("src/main/resources").getAbsolutePath();
            Path path = Paths.get(resourcesPath + "/uploaded-files/" + file.getOriginalFilename());
            Files.createDirectories(path.getParent());
            file.transferTo(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(readOnly = true)
    public InvestmentPieceListResponse getListedList(){
        var pieceInvestments = pieceInvestmentRepository.findAll();

        var lands = pieceInvestments.stream()
            .filter(piece -> "land".equals(piece.getType()))
            .map(this::convertToSubDto)
            .collect(Collectors.toList());

        var estates = pieceInvestments.stream()
            .filter(piece -> "estate".equals(piece.getType()))
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
        return new NotificationDetailResponse(new NotificationDetailSubResponse(Long.parseLong(orderPieceId), user.getName(), order.getType(), notification.getMessage(), order.getCount(), LocalDateTime.now(), Integer.parseInt(order.getTotalPrice())));
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
            if(pieceInvest.getType().equals("land")){
                landResponse.add(sub);
            }else{
                estateResponse.add(sub);
            }
        });

        return new NotificationResponse(estateResponse, landResponse);
    }


    private InvestmentPieceListSubResponse convertToSubDto(PieceInvestment pieceInvestment) {
        InvestmentPieceListSubResponse subDto = new InvestmentPieceListSubResponse();
        subDto.setInvestedPieceId(pieceInvestment.getPieceInvestmentId().toString());
        subDto.setSalesCompleted(pieceInvestment.isSaleCompleted());
        subDto.setName(pieceInvestment.getName());
        return subDto;
    }
}
