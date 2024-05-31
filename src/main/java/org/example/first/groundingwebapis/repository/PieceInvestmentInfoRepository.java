package org.example.first.groundingwebapis.repository;

import org.example.first.groundingwebapis.entity.AssetFiles;
import org.example.first.groundingwebapis.entity.PieceInvestmentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PieceInvestmentInfoRepository extends JpaRepository<PieceInvestmentInfo, Long> {

}
