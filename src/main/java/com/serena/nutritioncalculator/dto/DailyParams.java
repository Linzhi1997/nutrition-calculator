package com.serena.nutritioncalculator.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DailyParams {
    private Integer dailyCal;
    private Integer dailyCarbs;
    private Integer dailyProtein;
    private Integer dailyFat;
    private Integer recommendCal;
    private String  achievePercent;
}
