package org.example.first.groundingwebapis.repository;

import org.example.first.groundingwebapis.entity.Disclosure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DisclosureRepository extends JpaRepository<Disclosure, Long> {

    List<Disclosure> findByPieceInvestmentId(@Param("pieceInvestmentId") Long pieceInvestmentId);
}