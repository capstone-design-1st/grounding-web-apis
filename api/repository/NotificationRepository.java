package com.grounding.api.repository;

import com.grounding.api.entity.InvestmentStatus;
import com.grounding.api.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Notification findByOrderPieceId(@Param("order_piece_id") Long orderPieceId);
}
