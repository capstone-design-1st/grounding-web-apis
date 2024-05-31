package org.example.first.groundingwebapis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvestmentPieceResponse {
    private InvestmentPieceListSubResponse estates;
    private InvestmentPieceListSubResponse lands;
}
