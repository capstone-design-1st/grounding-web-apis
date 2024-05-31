package org.example.first.groundingwebapis.repository;

import org.example.first.groundingwebapis.entity.InvestmentStatus;
import org.example.first.groundingwebapis.entity.PieceInvestment;
import org.example.first.groundingwebapis.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InvestmentStatusRepository extends JpaRepository<InvestmentStatus, Long> {

    InvestmentStatus findByUserId(@Param("user_id") Long userId);

}
