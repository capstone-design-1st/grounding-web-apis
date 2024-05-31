package org.example.first.groundingwebapis.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "order_piece")
public class OrderPiece {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderPieceId;

    @Column(name = "order_id", nullable = false, unique = true)
    private Long orderId;

    @Column(name = "piece_investment_id", nullable = false)
    private Integer pieceInvestmentId;

    @Column(name = "count", nullable = false)
    private Integer count;

}
