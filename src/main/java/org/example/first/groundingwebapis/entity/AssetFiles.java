package org.example.first.groundingwebapis.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "asset_files")
public class AssetFiles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "piece_investment_id")
    private Long pieceInvestmentId;

    @Column(name = "document_type", nullable = false)
    private String documentType;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "admin_yn", nullable = false)
    private String adminYn;

    public void updateYn(String adminYn){
        this.adminYn = adminYn;
    }

    public AssetFiles(Long userId, Long pieceInvestmentId, String documentType, String fileName) {
        this.userId = userId;
        this.pieceInvestmentId = pieceInvestmentId;
        this.documentType = documentType;
        this.fileName = fileName;
        this.adminYn = "N";
    }

    public AssetFiles() {

    }
}