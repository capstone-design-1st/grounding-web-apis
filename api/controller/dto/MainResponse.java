package com.grounding.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainResponse {

    @JsonProperty("estates")
    private List<MainSubResponse> estates;

    @JsonProperty("lands")
    private List<MainSubResponse> lands;
}
