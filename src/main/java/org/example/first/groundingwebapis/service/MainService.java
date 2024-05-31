package org.example.first.groundingwebapis.service;

import org.example.first.groundingwebapis.dto.*;
import org.example.first.groundingwebapis.entity.PieceInvestment;
import org.example.first.groundingwebapis.repository.InvestmentStatusRepository;
import org.example.first.groundingwebapis.repository.PieceInvestmentRepository;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class MainService {

    private final PieceInvestmentRepository pieceInvestmentRepository;
    private final InvestmentStatusRepository investmentStatusRepository;

    @Transactional(readOnly = true)
    public MainResponse getSaleStatus(){
        var pieceInvestments = pieceInvestmentRepository.findAll();

        var lands = pieceInvestments.stream()
                .filter(piece -> "LAND".equals(piece.getAssetType()))
                .map(this::convertToSubDto)
                .collect(Collectors.toList());

        var estates = pieceInvestments.stream()
                .filter(piece -> "ESTATE".equals(piece.getAssetType()))
                .map(this::convertToSubDto)
                .collect(Collectors.toList());

        return new MainResponse(estates, lands);
    }

    @Transactional(readOnly = true)
    public MainMyResponse getMySaleStatusList(){
        var pieceInvestments = pieceInvestmentRepository.findByUserId(1L);

        var lands = pieceInvestments.stream()
                .filter(piece -> "LAND".equals(piece.getAssetType()))
                .map(this::convertToSubDto)
                .collect(Collectors.toList());

        var estates = pieceInvestments.stream()
                .filter(piece -> "ESTATE".equals(piece.getAssetType()))
                .map(this::convertToSubDto)
                .collect(Collectors.toList());

        var profits = pieceInvestments.stream()
                .filter(piece -> piece.isSaleCompleted())
                .map(this::convertToSubDto)
                .collect(Collectors.toList());

        return new MainMyResponse(estates, lands, profits);
    }

    @Transactional(readOnly = true)
    public MainListResponse getSaleStatusList(){
        var countList = pieceInvestmentRepository.count();
        return new MainListResponse(String.valueOf(countList));
    }

    @Transactional(readOnly = true)
    public MainSellerResponse getSellerInfo(String investmentPieceId){
        var user = pieceInvestmentRepository.findUserByJoiningPieceId(investmentPieceId);
        var investmentStatus = investmentStatusRepository.findByUserId(user.getUserId());

        return new MainSellerResponse(
                new MainSellerDetailResponse(user.getName(), investmentStatus.getSelledTotalCount(), investmentStatus.getInvestmentReturn())
        );
    }

    @Transactional(readOnly = true)
    public MainStatusResponse getMainStatus(){
        var pieceInvestment = pieceInvestmentRepository.findAll();
        List<MainStatusSubResponse> notCompleted = new ArrayList<>();
        List<MainStatusCompSubResponse> completed = new ArrayList();
        for (PieceInvestment a : pieceInvestment) {
            if (a.isSaleCompleted()) {
                MainStatusCompSubResponse complete = new MainStatusCompSubResponse();
                complete.setInvestment_piece_id(a.getPieceInvestmentId());
                complete.setName(a.getName());
                complete.setPrice(a.getPrice());
                complete.setReturns(investmentStatusRepository.findByUserId(1L).getInvestmentReturn().toString() + "%");
                completed.add(complete);
            } else {
                MainStatusSubResponse notComp = new MainStatusSubResponse();
                var total =  investmentStatusRepository.findByUserId(1L).getSelledTotalCount();
                var resCount = a.getPieceCount() - total;
                var rate = (double) total / resCount;
                notComp.setName(a.getName());
                notComp.setInvestment_piece_id(a.getPieceInvestmentId());
                notComp.setRegister_pieces(a.getPieceCount());
                notComp.setSaled_pieces(a.getPrice());
                notComp.setCompletion_rate(String.valueOf(Math.round(rate * 1000) / 1000.0) + "%");
                notCompleted.add(notComp);
            }
        }
        return new MainStatusResponse(notCompleted, completed);
    }

    private MainSubResponse convertToSubDto(PieceInvestment pieceInvestment) {
        MainSubResponse subDto = new MainSubResponse();
        subDto.setInvestment_piece_id(pieceInvestment.getPieceInvestmentId().toString());
        subDto.setRegister_date(pieceInvestment.getDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")).toString());
        subDto.setName(pieceInvestment.getName());
        return subDto;
    }
}
