package org.example.first.groundingwebapis.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.example.first.groundingwebapis.entity.AssetFiles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetFilesRepository extends JpaRepository<AssetFiles, Long> {

    List<AssetFiles> findByPieceInvestmentId(@Param("pieceInvestmentId") Long id);
}
