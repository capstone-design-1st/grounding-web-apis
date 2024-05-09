package com.grounding.api.repository;

import com.grounding.api.entity.PieceInvestment;
import com.grounding.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PieceInvestmentRepository extends JpaRepository<PieceInvestment, Long> {
    @Query("SELECT a FROM User a JOIN PieceInvestment b ON a.userId = b.userId WHERE b.pieceInvestmentId = :investmentPieceId")
    User findUserByJoiningPieceId(@Param("investmentPieceId") String investmentPieceId);

    PieceInvestment findByLocate(@Param("locate") String locate);
}
