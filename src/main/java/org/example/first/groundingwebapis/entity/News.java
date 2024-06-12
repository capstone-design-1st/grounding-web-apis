package org.example.first.groundingwebapis.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.first.groundingwebapis.dto.NewsDto;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "news")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class News {

    @Id
    @Column(name = "news_id", columnDefinition = "BINARY(16)", nullable = false)
    private Long id;

    @PrePersist
    public void prePersist() {
        if (this.id == null)
            this.id = (long) UUID.randomUUID().toString().hashCode();
    }

    @Column(name = "piece_investment_id")
    private Long pieceInvestmentId;

    @Column(name = "title", length = 50)
    private String title;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "reported_at")
    private String reportedAt;


    @Builder
    public News(Long pieceInvestmentId, String title,
                String reportedAt,
                String publisher) {
        this.title = title;
        this.reportedAt = reportedAt;
        this.publisher = publisher;
    }

    public NewsDto toDto() {
        return NewsDto.builder()
                .title(title)
                .reportedAt(reportedAt)
                .publisher(publisher)
                .build();
    }
}
