package com.grounding.api.controller;

import com.grounding.api.controller.dto.InvestmentPieceListDeleteRequest;
import com.grounding.api.controller.dto.InvestmentPieceListResponse;
import com.grounding.api.controller.dto.InvestmentPieceRequest;
import com.grounding.api.controller.dto.MainListResponse;
import com.grounding.api.controller.dto.MainResponse;
import com.grounding.api.controller.dto.MainSellerResponse;
import com.grounding.api.controller.dto.NotificationDetailResponse;
import com.grounding.api.controller.dto.NotificationResponse;
import com.grounding.api.service.InvestmentPieceService;
import com.grounding.api.service.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/investment-piece")
@RequiredArgsConstructor
public class InvestmentPieceController {

    private final InvestmentPieceService investmentPieceService;


    @PostMapping
    public ResponseEntity<Void> setInvestmentPiece(@RequestBody InvestmentPieceRequest request) {
        investmentPieceService.setInvestmentPiece(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/estate-file")
    public ResponseEntity<?> setFiles(@RequestPart("document_type") String documentType,
        @RequestPart("file_name") MultipartFile file) {
        investmentPieceService.setFiles(documentType, file);
        return ResponseEntity.ok().build();
    }



    @GetMapping("/list")
    public ResponseEntity<InvestmentPieceListResponse> getListedList(){
        return ResponseEntity.ok(investmentPieceService.getListedList());
    }



    @DeleteMapping("/list/{investment-piece-id}")
    public ResponseEntity<Void> deleteListedList(@PathVariable(name = "investment-piece-id") String investmentPieceId, @RequestBody
        InvestmentPieceListDeleteRequest request){
        investmentPieceService.deleteListedList(request, investmentPieceId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/notification/{order-piece-id}")
    public ResponseEntity<NotificationDetailResponse> getNotificationDetail(@PathVariable(name = "order-piece-id") String orderPieceId){
        return ResponseEntity.ok(investmentPieceService.getNotificationDetail(orderPieceId));
    }

    @GetMapping("/notification")
    public ResponseEntity<NotificationResponse> getNotification(){
        return ResponseEntity.ok(investmentPieceService.getNotification());
    }
}
