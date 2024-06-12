package org.example.first.groundingwebapis.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class InvestmentPieceListSubResponse {

    private String investedPieceId;
    private String assetName;
    //private boolean salesCompleted;
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
    private double floor_area_ratio;
    private String building_date;
    private boolean automatic_close_flag;
    private String assetType; // ESTATE, LAND
    private String entryStatus; // Y , N
    private String landClassification;
    private String recommendedUse;
    private String desiredPrice;
    private Integer pricePerUnit;
    private String investmentPoint;
    private String landImageRegistration;
    private String leaseStartDate;
    private String leaseEndDate;
    private String assetImage;
    private String assetCertificateUrl;
    private String walletAddress;
}
