package org.example.first.groundingwebapis.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notifications")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @Column(name = "notification_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "piece_investment_id", nullable = false, foreignKey = @ForeignKey(name = "fk_notifications_piece_investment"))
    private PieceInvestment pieceInvestment;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "progress_rate")
    private Double progressRate;

    @Column(name = "notification_time")
    private LocalDateTime notificationTime;
//@PrePersist

    @PrePersist
    public void prePersist() {
        if (this.notificationTime == null)
            this.notificationTime = LocalDateTime.now();
    }

    public void updatePieceInvestment(PieceInvestment pieceInvestment) {
        this.pieceInvestment = pieceInvestment;
        pieceInvestment.addNotification(this);
    }

    @Builder
    public Notification(OrderPiece orderPiece, String userName, Integer quantity, Double progressRate, LocalDateTime notificationTime) {
        this.userName = userName;
        this.quantity = quantity;
        this.progressRate = progressRate;
        this.notificationTime = notificationTime;
    }
}
