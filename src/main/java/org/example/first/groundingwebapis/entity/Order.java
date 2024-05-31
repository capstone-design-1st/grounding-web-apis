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
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "total_price", nullable = false)
    private String totalPrice;

    @Column(name = "datetime", nullable = false)
    private LocalDateTime datetime;

    @Column(name = "count")
    private Integer count;

}
