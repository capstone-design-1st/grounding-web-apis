package org.example.first.groundingwebapis.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Entity
@Table(name = "order_piece")
public class OrderPiece {

    @Id
    @Column(name = "order_piece_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderPieceId;

    @Column(name = "order_id", nullable = false, unique = true)
    private Long orderId;

    @Column(name = "piece_investment_id", nullable = false)
    private Integer pieceInvestmentId;

    @Column(name = "count", nullable = false)
    private Integer count;

}
