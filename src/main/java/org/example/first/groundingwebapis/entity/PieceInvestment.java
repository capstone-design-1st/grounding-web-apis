package org.example.first.groundingwebapis.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

import lombok.Getter;

@Getter
@Entity
@Table(name = "piece_investment")
public class PieceInvestment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pieceInvestmentId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "name")
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "piece_count", nullable = false)
    private Integer pieceCount;

    @Column(name = "locate", nullable = false)
    private String locate;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "asset_certificate_url")
    private String assetCertificateUrl;

    @Column(name = "other_information")
    private String otherInformation;

    @Column(name = "floors", nullable = false)
    private Integer floors;

    @Column(name = "use_area", nullable = false)
    private String useArea;

    @Column(name = "main_use", nullable = false)
    private String mainUse;

    @Column(name = "land_area", nullable = false)
    private double landArea;

    @Column(name = "total_area", nullable = false)
    private double totalArea;

    @Column(name = "building_to_rand_ratio", nullable = false)
    private double buildingToRandRatio;

    @Column(name = "floor_area_ratio", nullable = false)
    private double floorAreaRatio;

    @Column(name = "building_date", nullable = false)
    private LocalDateTime buildingDate;

    @Column(name = "automatic_close_flag", nullable = false)
    private boolean automaticCloseFlag;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "sale_completed", nullable = false)
    private boolean saleCompleted;

    @Column(name = "asset_image")
    private String assetImage;

    @Column(name = "asset_type", nullable = false)
    private String assetType;

    @Column(name = "entry_status")
    private String entryStatus;

    @Column(name = "location_address")
    private String locationAddress;

    @Column(name = "land_classification")
    private String landClassification;

    @Column(name = "recommended_use")
    private String recommendedUse;

    @Column(name = "desired_price")
    private String desiredPrice;

    @Column(name = "price_per_unit")
    private Integer pricePerUnit;

    @Column(name = "additional_notes")
    private String additionalNotes;

    @Column(name = "land_image_registration")
    private String landImageRegistration;

    @Column(name = "lease_start_date")
    private String leaseStartDate;

    @Column(name = "lease_end_date")
    private String leaseEndDate;
    public PieceInvestment(String type, String locate, Integer price, String otherInformation, Integer floors,
                           String useArea, String mainUse, double landArea, double totalArea, double buildingToRandRatio,
                           double floor_area_ratio, LocalDateTime buildingDate, boolean automaticCloseFlag, Integer pricePerUnit, String assetType,
                           String entryStatus,String desiredPrice, Integer pieceCount, String leaseStartDate, String leaseEndDate,String assetImage, Long userId) {
        this.type = type;
        this.locate = locate;
        this.price = price;
        this.otherInformation = otherInformation;
        this.floors = floors;
        this.useArea = useArea;
        this.mainUse = mainUse;
        this.landArea = landArea;
        this.totalArea = totalArea;
        this.buildingToRandRatio = buildingToRandRatio;
        this.floorAreaRatio = floor_area_ratio;
        this.buildingDate = buildingDate;
        this.automaticCloseFlag = automaticCloseFlag;
        this.date = LocalDateTime.now();
        this.pricePerUnit = pricePerUnit;
        this.assetType = assetType;
        this.entryStatus = entryStatus;
        this.desiredPrice = desiredPrice;
        this.pieceCount = pieceCount;
        this.leaseStartDate = leaseStartDate;
        this.leaseEndDate = leaseEndDate;
        this.assetImage = assetImage;
        this.userId = userId;
    }

    public PieceInvestment(String type, String locate, Integer price, String otherInformation, Integer floors,
                           String useArea, String mainUse, double landArea, double totalArea, double buildingToRandRatio,
                           double floor_area_ratio, LocalDateTime buildingDate, boolean automaticCloseFlag, String assetType,String entryStatus,
                             String landClassification,String recommendedUse,String desiredPrice,Integer pricePerUnit,String additionalNotes,
                           String landImageRegistration,Integer pieceCount,String leaseStartDate, String leaseEndDate, String assetImage, Long userId) {
        this.type = type;
        this.locate = locate;
        this.price = price;
        this.otherInformation = otherInformation;
        this.floors = floors;
        this.useArea = useArea;
        this.mainUse = mainUse;
        this.landArea = landArea;
        this.totalArea = totalArea;
        this.buildingToRandRatio = buildingToRandRatio;
        this.floorAreaRatio = floor_area_ratio;
        this.buildingDate = buildingDate;
        this.automaticCloseFlag = automaticCloseFlag;
        this.date = LocalDateTime.now();
        this.assetType = assetType;
        this.entryStatus = entryStatus;
        this.landClassification = landClassification;
        this.recommendedUse = recommendedUse;
        this.desiredPrice = desiredPrice;
        this.pricePerUnit = pricePerUnit;
        this.additionalNotes = additionalNotes;
        this.landImageRegistration = landImageRegistration;
        this.pieceCount = pieceCount;
        this.leaseStartDate = leaseStartDate;
        this.leaseEndDate = leaseEndDate;
        this.assetImage = assetImage;
        this.userId = userId;
    }


    public PieceInvestment() {

    }
}
