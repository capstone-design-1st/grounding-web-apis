package org.example.first.groundingwebapis.controller;

import org.example.first.groundingwebapis.dto.*;
import org.example.first.groundingwebapis.security.UserPrincipal;
import org.example.first.groundingwebapis.service.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    @GetMapping
    public ResponseEntity<MainResponse> getSaleStatus() {
        return ResponseEntity.ok(mainService.getSaleStatus());
    }

    @GetMapping("/my")
    public ResponseEntity<MainMyResponse> getMySaleStatusList(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getUser().getUserId();
        return ResponseEntity.ok(mainService.getMySaleStatusList(userId));
    }


    @GetMapping("/list")
    public ResponseEntity<MainListResponse> getSaleStatusList() {
        return ResponseEntity.ok(mainService.getSaleStatusList());
    }

    @GetMapping("/{investment-piece-id}")
    public ResponseEntity<MainSellerResponse> getSellerInfo(@PathVariable(name = "investment-piece-id") String investmentPieceId){
        return ResponseEntity.ok(mainService.getSellerInfo(investmentPieceId));
    }

    @GetMapping("/status")
    public ResponseEntity<MainStatusResponse> getMainStatus(@AuthenticationPrincipal UserPrincipal userPrincipal){
        Long userId = userPrincipal.getUser().getUserId();
        return ResponseEntity.ok(mainService.getMainStatus(userId));
    }
}
