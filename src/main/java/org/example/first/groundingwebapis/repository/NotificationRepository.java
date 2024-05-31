package org.example.first.groundingwebapis.repository;

import org.example.first.groundingwebapis.entity.InvestmentStatus;
import org.example.first.groundingwebapis.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Notification findByOrderPieceId(@Param("order_piece_id") Long orderPieceId);
}