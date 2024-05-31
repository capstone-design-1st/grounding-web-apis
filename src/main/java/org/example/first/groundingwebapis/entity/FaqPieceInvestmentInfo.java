package org.example.first.groundingwebapis.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "faq_piece_investment_info")
public class FaqPieceInvestmentInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "faq_piece_investment_info_title")
    private String faqPieceInvestmentInfoTitle;

    @Column(name = "faq_piece_investment_info_content")
    private String faqPieceInvestmentInfoContent;


    public FaqPieceInvestmentInfo() {

    }
}