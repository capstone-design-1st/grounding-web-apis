package org.example.first.groundingwebapis.dto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvestmentPieceListResponse {
    private List<InvestmentPieceListSubResponse> estates;
    private List<InvestmentPieceListSubResponse> lands;
}
