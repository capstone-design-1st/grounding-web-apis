package org.example.first.groundingwebapis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.first.groundingwebapis.entity.AssetFiles;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainMyResponse {

    @JsonProperty("estates")
    private List<MainSubResponse> estates;

    @JsonProperty("lands")
    private List<MainSubResponse> lands;

    @JsonProperty("profits")
    private List<MainSubResponse> profits;

    @JsonProperty("thumbnail")
    private List<AssetFiles> thumbnailImage;
}
