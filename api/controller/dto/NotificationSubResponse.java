package com.grounding.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationSubResponse {
    @JsonProperty("order_piece_id")
    private Long order_piece_id;
    private String type;
    private String message;
    private Integer count;
    @JsonProperty("total_price")
    private Integer total_price;
}