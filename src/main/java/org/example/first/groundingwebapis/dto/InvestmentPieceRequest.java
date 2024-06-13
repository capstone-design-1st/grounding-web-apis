package org.example.first.groundingwebapis.dto;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class InvestmentPieceRequest {
    private String type;
    private String location;
    private Integer price;
    private String info;
    private Integer floors;
    private Integer piece_count;
    private String use_area;
    private String main_use;
    private double land_area;
    private double total_area;
    private double building_to_rand_ratio;
    private Integer floor_area_ratio;
    private String building_date;
    private boolean automatic_close_flag;
    private String assetType; // ESTATE, LAND
    private String entryStatus; // Y , N
    private String landClassification;
    private String recommendedUse;
    private String desiredPrice;
    private Integer pricePerUnit;
    private String oneline;
    private String landImageRegistration;
    private String leaseStartDate;
    private String leaseEndDate;
    private String assetImage;
    private String assetCertificateUrl;
    private String walletAddress;
    private String assetName;
}
