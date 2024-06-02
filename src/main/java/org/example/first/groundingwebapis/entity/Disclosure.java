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

    public Disclosure() {
    }

    public Disclosure(Long pieceInvestmentId, String assetAddress, String assetName, String disclosureTitle, String disclosureContent, String fileName) {
        this.pieceInvestmentId = pieceInvestmentId;
        this.assetAddress = assetAddress;
        this.assetName = assetName;
        this.disclosureTitle = disclosureTitle;
        this.disclosureContent = disclosureContent;
        this.date = LocalDateTime.now();
    }

    public void updateDisclosure(Long pieceInvestmentId, String assetAddress, String assetName, String disclosureTitle, String disclosureContent) {
        this.pieceInvestmentId = pieceInvestmentId;
        this.assetAddress = assetAddress;
        this.assetName = assetName;
        this.disclosureTitle = disclosureTitle;
        this.disclosureContent = disclosureContent;
    }

    @Column(name = "disclosure_title", nullable = false)
    private String disclosureTitle;

    @Column(name = "disclosure_content", nullable = false)
    private String disclosureContent;

    @Column
    private LocalDateTime date;

    public Disclosure(Long pieceInvestmentId, String assetAddress, String assetName, String disclosureTitle, String disclosureContent) {

    }
}
