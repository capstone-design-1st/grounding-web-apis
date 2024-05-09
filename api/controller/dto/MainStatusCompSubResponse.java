package com.grounding.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainStatusCompSubResponse {
    @JsonProperty("investment_piece_id")
    private Long investment_piece_id;
    private String name;
    private Integer price;
    @JsonProperty("return")
    private String returns;
}
