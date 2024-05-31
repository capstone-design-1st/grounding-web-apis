package org.example.first.groundingwebapis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminAssetYnRequest {
    private Long assetFileId;
    private String adminYn; // 승인여부 Y :승인 N : 거절
}