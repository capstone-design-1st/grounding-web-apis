package org.example.first.groundingwebapis.dto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.example.first.groundingwebapis.entity.AssetFiles;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@NoArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TransferToAppDto {
    // From InvestmentPieceRequest
    private String type;
    private String location;
    private Integer price;
    private String info;
    private Integer floors;
    private Integer pieceCount;
    private String useArea;
    private String mainUse;
    private double landArea;
    private double totalArea;
    private double buildingToRandRatio;
    private Integer floorAreaRatio;
    private String buildingDate;
    private boolean automaticCloseFlag;
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

    // From AdminAssetFileListsResponse
    private Long assetFileId;
    private Long userId;
    private String documentType;
    private String fileName;
    private String adminYn;

    // From NewsDto
    private Long newsId;
    private Long pieceInvestmentId;
    private String newsTitle;
    private String reportedAt;
    private String publisher;

    // From MainMyResponse
    private List<AssetFiles> thumbnailImage;

    // Constructor with NewsDto Builder fields
    @Builder
    public TransferToAppDto(String type, String location, Integer price, String info, Integer floors, Integer pieceCount, String useArea,
                         String mainUse, double landArea, double totalArea, double buildingToRandRatio, Integer floorAreaRatio,
                         String buildingDate, boolean automaticCloseFlag, String assetType, String entryStatus, String landClassification,
                         String recommendedUse, String desiredPrice, Integer pricePerUnit, String investmentPoint, String landImageRegistration,
                         String leaseStartDate, String leaseEndDate, String assetImage, String assetCertificateUrl, Long assetFileId,
                         Long userId, String documentType, String fileName, String adminYn, Long newsId, Long pieceInvestmentId,
                         String newsTitle, String reportedAt, String publisher, List<AssetFiles> thumbnailImage) {
        this.type = type;
        this.location = location;
        this.price = price;
        this.info = info;
        this.floors = floors;
        this.pieceCount = pieceCount;
        this.useArea = useArea;
        this.mainUse = mainUse;
        this.landArea = landArea;
        this.totalArea = totalArea;
        this.buildingToRandRatio = buildingToRandRatio;
        this.floorAreaRatio = floorAreaRatio;
        this.buildingDate = buildingDate;
        this.automaticCloseFlag = automaticCloseFlag;
        this.assetType = assetType;
        this.entryStatus = entryStatus;
        this.landClassification = landClassification;
        this.recommendedUse = recommendedUse;
        this.desiredPrice = desiredPrice;
        this.pricePerUnit = pricePerUnit;
        this.investmentPoint = investmentPoint;
        this.landImageRegistration = landImageRegistration;
        this.leaseStartDate = leaseStartDate;
        this.leaseEndDate = leaseEndDate;
        this.assetImage = assetImage;
        this.assetCertificateUrl = assetCertificateUrl;
        this.assetFileId = assetFileId;
        this.userId = userId;
        this.documentType = documentType;
        this.fileName = fileName;
        this.adminYn = adminYn;
        this.newsId = newsId;
        this.pieceInvestmentId = pieceInvestmentId;
        this.newsTitle = newsTitle != null ? newsTitle : "";
        this.reportedAt = reportedAt != null ? reportedAt : LocalDate.now().format(DateTimeFormatter.ISO_DATE);
        this.publisher = publisher != null ? publisher : "";
        this.thumbnailImage = thumbnailImage;
    }
}
