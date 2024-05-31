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
@Table(name = "investment_status")
public class InvestmentStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long investmentStatusId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "selled_total_count", nullable = false)
    private Integer selledTotalCount;

    @Column(name = "investment_return", nullable = false)
    private Double investmentReturn;

}