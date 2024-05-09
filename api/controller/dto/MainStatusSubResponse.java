package com.grounding.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainStatusSubResponse {
    @JsonProperty("investment_piece_id")
    private Long investment_piece_id;
    private String name;
    @JsonProperty("register_pieces")
    private Integer register_pieces;
    @JsonProperty("saled_pieces")
    private Integer saled_pieces;
    @JsonProperty("completion_rate")
    private String completion_rate;
}
