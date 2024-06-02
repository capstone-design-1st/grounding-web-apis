package org.example.first.groundingwebapis.controller;

import org.example.first.groundingwebapis.dto.*;
import org.example.first.groundingwebapis.service.InvestmentPieceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

    @PostMapping("/asset-file")
    public ResponseEntity<?> setFiles(
            @RequestPart("piece_investment_id") Long pieceInvestmentId,
            @RequestPart("file_name") MultipartFile file,
            @RequestPart("image_files") MultipartFile[] files) throws IOException {
        return ResponseEntity.ok(investmentPieceService.setFiles(pieceInvestmentId, file, files));
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

    // 공시조회
    @GetMapping("/disclosure/{piece-investment-id}")
    public ResponseEntity<List<DisclosureResponse>> getDisclosureList(@PathVariable(name = "piece-investment-id") Long id){
        return ResponseEntity.ok(investmentPieceService.getDisclosureList(id));
    }

    // 공시등록 및 수정
    @PostMapping("/disclosure")
    public ResponseEntity<Void> setDisclosure(@RequestPart("piece_investment_id") Long pieceInvestmentId,
                                              @RequestPart("asset_address") String assetAddress,
                                              @RequestPart("asset_name") String assetName,
                                              @RequestPart("disclosure_title") String disclosureTitle,
                                              @RequestPart("disclosure_content") String disclosureContent) throws IOException {
        investmentPieceService.setDisclosure(pieceInvestmentId, assetAddress, assetName, disclosureTitle, disclosureContent);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/disclosure/{disclosure-id}")
    public ResponseEntity<Void> deleteDisclosure(@PathVariable(name = "disclosure-id") Long id){
        investmentPieceService.deleteDisclosure(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/disclosure")
    public ResponseEntity<Void> setDisclosure(@RequestPart("disclosure_id") Long disclosureId,
                                              @RequestPart("piece_investment_id") Long pieceInvestmentId,
                                              @RequestPart("asset_address") String assetAddress,
                                              @RequestPart("asset_name") String assetName,
                                              @RequestPart("disclosure_title") String disclosureTitle,
                                              @RequestPart("disclosure_content") String disclosureContent) throws IOException {
        investmentPieceService.updateDisclosure(disclosureId, pieceInvestmentId, assetAddress, assetName, disclosureTitle, disclosureContent);
        return ResponseEntity.ok().build();
    }

    //로그인 전 조각판매 알아보기 API
    @GetMapping("/learn")
    public ResponseEntity<List<PieceInvestmentInfoResponse>> getPieceInvestmentInfo(){
        return ResponseEntity.ok(investmentPieceService.getPieceInvestmentInfo());
    }

    //로그인 전 모집 중 매물구경 API
    @GetMapping("/approved")
    public ResponseEntity<InvestmentPieceListResponse> getApprovedPieceInvestmentInfoResponse(){
        return ResponseEntity.ok(investmentPieceService.getApprovedPieceInvestmentInfoResponse());
    }

    //로그인 전 자주 묻는 질문 API
    @GetMapping("/qna")
    public ResponseEntity<List<FaqPieceInvestmentInfoResponse>> getFaqPieceInvestmentInfo(){
        return ResponseEntity.ok(investmentPieceService.getFaqPieceInvestmentInfo());
    }

    //종목정보 API
    @GetMapping("/info/{piece_investment_id}")
    public ResponseEntity<InvestmentPieceResponse> getAssetInfo(@PathVariable("piece_investment_id") Long pieceInvestmentId){
        return ResponseEntity.ok(investmentPieceService.getAssetInfo(pieceInvestmentId));
    }
}
