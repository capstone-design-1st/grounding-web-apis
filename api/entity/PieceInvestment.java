package com.grounding.api.entity;

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

    @Column(name = "locate", nullable = false)
    private String locate;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "piece_count", nullable = false)
    private Integer pieceCount;

    @Column(name = "owner_certificate_url")
    private String ownerCertificateUrl;

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



    public PieceInvestment(String type, String locate, Integer price, String otherInformation, Integer floors,
        String useArea, String mainUse, double landArea, double totalArea, double buildingToRandRatio,
        double floor_area_ratio, LocalDateTime buildingDate, boolean automaticCloseFlag, Integer pieceCount, Long userId) {
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
        this.pieceCount = pieceCount;
        this.userId = userId;
    }


    public PieceInvestment() {

    }
}
