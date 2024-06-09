package org.example.first.groundingwebapis.repository;

import org.example.first.groundingwebapis.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findByPieceInvestmentId(@Param("pieceInvestmentId") Long pieceInvestmentId);
}
