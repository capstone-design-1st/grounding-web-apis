package org.example.first.groundingwebapis.repository;

import org.example.first.groundingwebapis.dto.NotificationDto;
import org.example.first.groundingwebapis.entity.InvestmentStatus;
import org.example.first.groundingwebapis.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationRepository extends JpaRepository<Notification, Long> {


    @Query("SELECT n FROM Notification n WHERE n.pieceInvestment.pieceInvestmentId = :pieceInvestmentId")
    Page<NotificationDto.GetResponse> readByPieceInvestmentId(Long pieceInvestmentId, Pageable pageable);
}
