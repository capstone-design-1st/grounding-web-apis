package org.example.first.groundingwebapis.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisclosureResponse {
    private Long id;
    private Long pieceInvestmentId;
    private String assetAddress;
    private String assetName;
    private String disclosureTitle;
    private String disclosureContent;
    private String date;
}
