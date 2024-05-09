package com.grounding.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvestmentPieceListSubResponse {

    @JsonProperty("invested_piece_id")
    private String investedPieceId;
    private String name;

    @JsonProperty("sales_completed")
    private boolean salesCompleted;
}
