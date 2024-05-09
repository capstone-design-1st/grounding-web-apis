package com.grounding.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainSubResponse {

    @JsonProperty("investment_piece_id")
    private String investment_piece_id;
    @JsonProperty("register_date")
    private String register_date;
    private String name;
}
