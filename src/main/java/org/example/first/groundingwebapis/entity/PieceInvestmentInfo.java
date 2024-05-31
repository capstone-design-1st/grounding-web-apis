package org.example.first.groundingwebapis.entity;

import jakarta.persistence.*;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "piece_investment_info")
public class PieceInvestmentInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "piece_investment_info_title")
    private String pieceInvestmentInfoTitle;

    @Column(name = "piece_investment_info_content")
    private String pieceInvestmentInfoContent;

    @Column(name = "date")
    private LocalDateTime date;

    public PieceInvestmentInfo() {

    }
}
