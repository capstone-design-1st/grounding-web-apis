package org.example.first.groundingwebapis.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class KafkaDto {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class UserInfo {
        private String name;
        private String email;
    }

    @Getter
    @NoArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class NotificationDto {
        /*
                this.orderPiece = orderPiece;
        this.userName = userName;
        this.quantity = quantity;
        this.progressRate = progressRate;
        this.notificationTime = notificationTime;
         */
        private String orderPieceName;
        private String userName;
        private Integer quantity;
        private Double progressRate;
        private LocalDateTime executedTime;

        @Builder
        public NotificationDto(String orderPieceName, String userName, Integer quantity, Double progressRate, LocalDateTime executedTime) {
            this.orderPieceName = orderPieceName;
            this.userName = userName;
            this.quantity = quantity;
            this.progressRate = progressRate;
            this.executedTime = executedTime;
        }
    }
}
