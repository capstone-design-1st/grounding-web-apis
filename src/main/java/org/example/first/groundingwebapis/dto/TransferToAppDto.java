package org.example.first.groundingwebapis.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.example.first.groundingwebapis.entity.AssetFiles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TransferToAppDto {

    // From NewsDto
    private String newsTitle;
    private String reportedAt;
    private String publisher;

    // From AdminAssetFileListsResponse
    private String fileName;

    // From MainMyResponse
    private List<AssetFiles> thumbnailImage;

    private Estate estate;
    private Land land;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Estate {
        private String assetType;
        private String location;
        private double total_area;
        private String investmentPoint;
        private String assetCertificateUrl;
        private String entryStatus;
        private String leaseStartDate;
        private String leaseEndDate;
        private Integer pricePerUnit;
        private String assetName;
        private String walletAddress;
        private Integer piece_count;
        private Integer floors;
        private String use_area;
        private String main_use;
        private double land_area;
        private double building_to_rand_ratio;
        private double floor_area_ratio;
        private String building_date;
        private boolean automatic_close_flag;
        private String assetImage;
        private Integer price;
        private String type;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Land {
        private String assetType;
        private String location;
        private double total_area;
        private String investmentPoint;
        private String assetCertificateUrl;
        private String entryStatus;
        private String leaseStartDate;
        private String leaseEndDate;
        private Integer pricePerUnit;
        private String assetName;
        private String walletAddress;
        private String landClassification;
        private String recommendedUse;
        private String desiredPrice;
        private String info;
        private String landImageRegistration;
        private Integer price;
        private String type;
    }
}
