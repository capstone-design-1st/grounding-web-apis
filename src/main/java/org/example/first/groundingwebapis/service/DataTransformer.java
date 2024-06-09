package org.example.first.groundingwebapis.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.first.groundingwebapis.dto.TransferToAppDto;
import org.example.first.groundingwebapis.dto.InvestmentPieceRequest;
import org.example.first.groundingwebapis.dto.AdminAssetFileListsResponse;
import org.example.first.groundingwebapis.dto.MainMyResponse;
import org.example.first.groundingwebapis.dto.NewsDto;

public class DataTransformer {
    private ObjectMapper objectMapper = new ObjectMapper();

    public TransferToAppDto transformToDTO(InvestmentPieceRequest investmentPieceRequest, AdminAssetFileListsResponse adminAssetFileListsResponse, MainMyResponse mainMyResponse, NewsDto newsDto) {
        TransferToAppDto transferToAppDto = new TransferToAppDto();

        if (mainMyResponse != null) {
            transferToAppDto.setThumbnailImage(mainMyResponse.getThumbnailImage());
        }

        if (investmentPieceRequest != null) {
            transferToAppDto.setAssetImage(investmentPieceRequest.getAssetImage());
            transferToAppDto.setLandImageRegistration(investmentPieceRequest.getLandImageRegistration());
            transferToAppDto.setInvestmentPoint(investmentPieceRequest.getInvestmentPoint());
            transferToAppDto.setType(investmentPieceRequest.getType());
            transferToAppDto.setLocation(investmentPieceRequest.getLocation());
            transferToAppDto.setPrice(investmentPieceRequest.getPrice());
            transferToAppDto.setInfo(investmentPieceRequest.getInfo());
            transferToAppDto.setFloors(investmentPieceRequest.getFloors());
            transferToAppDto.setPieceCount(investmentPieceRequest.getPieceCount());
            transferToAppDto.setUseArea(investmentPieceRequest.getUseArea());
            transferToAppDto.setMainUse(investmentPieceRequest.getMainUse());
            transferToAppDto.setLandArea(investmentPieceRequest.getLandArea());
            transferToAppDto.setTotalArea(investmentPieceRequest.getTotalArea());
            transferToAppDto.setBuildingToLandRatio(investmentPieceRequest.getBuildingToLandRatio());
            transferToAppDto.setFloorAreaRatio(investmentPieceRequest.getFloorAreaRatio());
            transferToAppDto.setBuildingDate(investmentPieceRequest.getBuildingDate());
            transferToAppDto.setAutomaticCloseFlag(investmentPieceRequest.isAutomaticCloseFlag());
            transferToAppDto.setAssetType(investmentPieceRequest.getAssetType());
            transferToAppDto.setEntryStatus(investmentPieceRequest.getEntryStatus());
            transferToAppDto.setLandClassification(investmentPieceRequest.getLandClassification());
            transferToAppDto.setRecommendedUse(investmentPieceRequest.getRecommendedUse());
            transferToAppDto.setDesiredPrice(investmentPieceRequest.getDesiredPrice());
            transferToAppDto.setPricePerUnit(investmentPieceRequest.getPricePerUnit());
            transferToAppDto.setLeaseStartDate(investmentPieceRequest.getLeaseStartDate());
            transferToAppDto.setLeaseEndDate(investmentPieceRequest.getLeaseEndDate());
            transferToAppDto.setAssetCertificateUrl(investmentPieceRequest.getAssetCertificateUrl());
        }

        if (adminAssetFileListsResponse != null) {
            transferToAppDto.setFileName(adminAssetFileListsResponse.getFileName());
        }

        if (newsDto != null) {
            transferToAppDto.setNewsTitle(newsDto.getTitle());
            transferToAppDto.setNewsReportedAt(newsDto.getReportedAt());
            transferToAppDto.setNewsPublisher(newsDto.getPublisher());
        }

        return transferToAppDto;
    }
}
