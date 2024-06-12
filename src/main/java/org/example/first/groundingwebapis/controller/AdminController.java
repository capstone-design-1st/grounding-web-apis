package org.example.first.groundingwebapis.controller;

import org.example.first.groundingwebapis.dto.AdminAssetFileListsResponse;
import org.example.first.groundingwebapis.dto.AdminAssetYnRequest;
import org.example.first.groundingwebapis.dto.AdminLoginRequest;
import org.example.first.groundingwebapis.dto.InvestmentPieceListResponse;
import org.example.first.groundingwebapis.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/assets")
    public ResponseEntity<List<AdminAssetFileListsResponse>> getAssetFileLists(){
        return ResponseEntity.ok(adminService.getAssetFileLists());
    }

    @PostMapping("/assets")
    public ResponseEntity<Void> setAssetYn(@RequestBody AdminAssetYnRequest request) throws Exception {
        adminService.setAssetYn(request);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody AdminLoginRequest request) throws Exception {
        if(adminService.adminLogin(request)){
            return ResponseEntity.ok().build();
        }else{
            throw new Exception();
        }
    }
}