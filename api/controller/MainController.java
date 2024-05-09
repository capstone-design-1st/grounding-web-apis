package com.grounding.api.controller;

import com.grounding.api.controller.dto.MainListResponse;
import com.grounding.api.controller.dto.MainResponse;
import com.grounding.api.controller.dto.MainSellerResponse;
import com.grounding.api.controller.dto.MainStatusResponse;
import com.grounding.api.service.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/list")
    public ResponseEntity<MainListResponse> getSaleStatusList() {
        return ResponseEntity.ok(mainService.getSaleStatusList());
    }

    @GetMapping("/{investment-piece-id}")
    public ResponseEntity<MainSellerResponse> getSellerInfo(@PathVariable(name = "investment-piece-id") String investmentPieceId){
        return ResponseEntity.ok(mainService.getSellerInfo(investmentPieceId));
    }

    @GetMapping("/status")
    public ResponseEntity<MainStatusResponse> getMainStatus(){
        return ResponseEntity.ok(mainService.getMainStatus());
    }
}
