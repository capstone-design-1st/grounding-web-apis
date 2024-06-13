package org.example.first.groundingwebapis.repository;

import org.example.first.groundingwebapis.entity.PieceInvestment;
import org.example.first.groundingwebapis.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PieceInvestmentRepository extends JpaRepository<PieceInvestment, Long> {
    @Query("SELECT a FROM User a JOIN PieceInvestment b ON a.userId = b.userId WHERE b.pieceInvestmentId = :investmentPieceId")
    User findUserByJoiningPieceId(@Param("investmentPieceId") String investmentPieceId);

    PieceInvestment findByLocate(@Param("locate") String locate);

    @Query("SELECT e FROM PieceInvestment e JOIN AssetFiles f ON e.pieceInvestmentId = f.pieceInvestmentId WHERE f.adminYn = 'Y'")
    List<PieceInvestment> findApprovedInfos();

    List<PieceInvestment> findByUserId(Long userId);

    @Query("SELECT e FROM PieceInvestment e JOIN AssetFiles f ON e.pieceInvestmentId = f.pieceInvestmentId WHERE f.adminYn = 'Y'")
    List<PieceInvestment> findApprovedPieceInvestmentList();

    @Query("SELECT p FROM PieceInvestment p WHERE p.assetName = :pieceInvestmentName")
    Optional<PieceInvestment> findByAssetName(String pieceInvestmentName);
}
