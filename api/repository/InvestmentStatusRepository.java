package com.grounding.api.repository;

import com.grounding.api.entity.InvestmentStatus;
import com.grounding.api.entity.PieceInvestment;
import com.grounding.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InvestmentStatusRepository extends JpaRepository<InvestmentStatus, Long> {

    InvestmentStatus findByUserId(@Param("user_id") Long userId);

}
