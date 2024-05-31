package org.example.first.groundingwebapis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisclosurePostRequest {
    private Long pieceInvestmentId;
    private String assetAddress;
    private String assetName;
    private String disclosureTitle;
    private String disclosureContent;
    private MultipartFile file;
}
