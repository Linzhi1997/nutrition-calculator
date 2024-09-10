package com.serena.nutritioncalculator.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DailyIntake {
    private Integer userId;
    private Integer recommendCal;
    private Integer dailyCal;
    private Integer dailyCarbs;
    private Integer dailyProtein;
    private Integer dailyFat;
    private String achievePercent;
}
