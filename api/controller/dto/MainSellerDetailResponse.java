package com.grounding.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainSellerDetailResponse {

    private String name;

    @JsonProperty("selled_total_count")
    private Integer selledTotalCount;

    @JsonProperty("investment_return")
    private Double investmentReturn;

}
