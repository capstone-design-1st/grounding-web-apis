package org.example.first.groundingwebapis.dto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.example.first.groundingwebapis.entity.AssetFiles;

import java.util.List;

@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TransferToAppDto {
    private List<AssetFiles> thumbnailImage;
    private String fileName;
    private String landImageRegistration;
    private String assetImage;

    private String newsTitle;
    private String newsReportedAt;
    private String newsPublisher;

    private String investmentPoint;

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
    private double building_to_land_ratio;
    private Integer floor_area_ratio;
    private String building_date;
    private boolean automatic_close_flag;
    private String assetType;
    private String entryStatus;
    private String landClassification;
    private String recommendedUse;
    private String desiredPrice;
    private Integer pricePerUnit;
    private String leaseStartDate;
    private String leaseEndDate;
    private String assetCertificateUrl;
}
