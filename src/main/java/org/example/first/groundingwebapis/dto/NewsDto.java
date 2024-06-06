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

@Data
@NoArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class NewsDto {

    private Long id;
    private String title;
    private LocalDate reportedAt;
    private String publisher;

    @Builder
    public NewsDto(Long id,  String title, LocalDate reportedAt, String publisher, String url) {
        this.id = id;
        this.title = title != null ? title : "";
        this.reportedAt = reportedAt != null ? reportedAt : LocalDate.now();
        this.publisher = publisher != null ? publisher : "";
    }

}