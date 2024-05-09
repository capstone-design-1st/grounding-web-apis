package com.grounding.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainStatusResponse {
    @JsonProperty("proceeding")
    private List<MainStatusSubResponse> proceeding;
    @JsonProperty("completion")
    private List<MainStatusCompSubResponse> completion;
}
