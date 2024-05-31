package org.example.first.groundingwebapis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDetailSubResponse {
    @JsonProperty("order_piece_id")
    private Long order_piece_id;
    private String trader;
    private String type;
    private String message;
    private Integer count;
    @JsonProperty("notification_time")
    private LocalDateTime notification_time;
    @JsonProperty("total_price")
    private Integer total_price;
}