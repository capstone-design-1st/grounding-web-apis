package org.example.first.groundingwebapis.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminAssetFileListsResponse {
    private Long assetFileId;
    private Long userId;
    private String documentType;
    private String fileName;
    private String adminYn;
    private String fileUrl;
}