package org.example.first.groundingwebapis;

import org.example.first.groundingwebapis.dto.TransferToAppDto;
import org.example.first.groundingwebapis.entity.AssetFiles;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ObjectMapperExample implements CommandLineRunner {

    private final ObjectMapper objectMapper;

    @Autowired
    public ObjectMapperExample(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        // DTO to JSON
        TransferToAppDto dto = new TransferToAppDto();
        dto.setType("ExampleType");
        dto.setLocation("ExampleLocation");
        dto.setPrice(1000);
        dto.setInfo("ExampleInfo");
        dto.setFloors(5);
        dto.setPieceCount(10);
        dto.setUseArea("Commercial");
        dto.setMainUse("Office");
        dto.setLandArea(200.5);
        dto.setTotalArea(1000.5);
        dto.setBuildingToRandRatio(0.5);
        dto.setFloorAreaRatio(2);
        dto.setBuildingDate("2023-06-09");
        dto.setAutomaticCloseFlag(true);
        dto.setAssetType("ESTATE");
        dto.setEntryStatus("Y");
        dto.setLandClassification("Residential");
        dto.setRecommendedUse("Housing");
        dto.setDesiredPrice("1000000");
        dto.setPricePerUnit(10000);
        dto.setInvestmentPoint("Good");
        dto.setLandImageRegistration("Yes");
        dto.setLeaseStartDate("2024-01-01");
        dto.setLeaseEndDate("2024-12-31");
        dto.setAssetImage("example.png");
        dto.setAssetCertificateUrl("http://example.com/certificate");

        List<AssetFiles> thumbnailImage = new ArrayList<>();
        dto.setThumbnailImage(thumbnailImage);

        try {
            String jsonString = objectMapper.writeValueAsString(dto);
            System.out.println("DTO to JSON: " + jsonString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // JSON to DTO
        String jsonString = "{\"type\":\"ExampleType\",\"location\":\"ExampleLocation\",\"price\":1000}";
        try {
            TransferToAppDto transferToApp = objectMapper.readValue(jsonString, TransferToAppDto.class);
            System.out.println("JSON to DTO: " + transferToApp);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
