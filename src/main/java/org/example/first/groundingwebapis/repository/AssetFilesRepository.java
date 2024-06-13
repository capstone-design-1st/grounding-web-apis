package org.example.first.groundingwebapis.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.example.first.groundingwebapis.entity.AssetFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AssetFilesRepository extends JpaRepository<AssetFiles, Long> {

    List<AssetFiles> findByPieceInvestmentId(@Param("pieceInvestmentId") Long id);

    @Query("SELECT af FROM AssetFiles af WHERE af.pieceInvestmentId = :id AND af.documentType = :documentType")
    List<AssetFiles> findByPieceInvestmentIdAndDocumentType(@Param("pieceInvestmentId") Long id, @Param("documentType") String documentType);
}
