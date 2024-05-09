package com.grounding.api.controller.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvestmentPieceRequest {
    private String type;
    private String location;
    private Integer price;
    private String info;
    private Integer floors;
    private Integer piece_count;
    private String use_area;
    private String main_use;
    private double land_area;
    private double total_area;
    private double building_to_rand_ratio;
    private Integer floor_area_ratio;
    private String building_date;
    private boolean automatic_close_flag;

}
