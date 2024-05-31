package org.example.first.groundingwebapis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainSellerResponse {

    @JsonProperty("seller_info")
    private MainSellerDetailResponse seller_info;
}
