package org.example.first.groundingwebapis.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "disclosure")
public class Disclosure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "piece_investment_id")
    private Long pieceInvestmentId;

    @Column(name = "asset_address", nullable = false)
    private String assetAddress;

    @Column(name = "asset_name", nullable = false)
    private String assetName;

    public void updateDisclosure(Long pieceInvestmentId, String assetAddress, String assetName, String disclosureTitle, String disclosureContent, String fileName) {
        this.pieceInvestmentId = pieceInvestmentId;
        this.assetAddress = assetAddress;
        this.assetName = assetName;
        this.disclosureTitle = disclosureTitle;
        this.disclosureContent = disclosureContent;
        this.fileName = fileName;
    }

    public Disclosure(Long pieceInvestmentId, String assetAddress, String assetName, String disclosureTitle, String disclosureContent, String fileName) {
        this.pieceInvestmentId = pieceInvestmentId;
        this.assetAddress = assetAddress;
        this.assetName = assetName;
        this.disclosureTitle = disclosureTitle;
        this.disclosureContent = disclosureContent;
        this.fileName = fileName;
        this.date = LocalDateTime.now();
    }

    @Column(name = "disclosure_title", nullable = false)
    private String disclosureTitle;

    @Column(name = "disclosure_content", nullable = false)
    private String disclosureContent;

    @Column(name = "file_name", nullable = false)
    private String fileName;


    @Column
    private LocalDateTime date;

    public Disclosure() {

    }
}
