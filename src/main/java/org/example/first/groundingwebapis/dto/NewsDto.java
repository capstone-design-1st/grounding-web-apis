package org.example.first.groundingwebapis.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class NewsDto {

    private Long id;
    private Long pieceInvestmentId;
    private String title;
    private String reportedAt;
    private String publisher;

    @Builder
    public NewsDto(String title, String reportedAt, String publisher) {
        this.title = title != null ? title : "";
        this.reportedAt = reportedAt != null ? reportedAt : LocalDate.now().format(DateTimeFormatter.ISO_DATE);
        this.publisher = publisher != null ? publisher : "";
    }

}
