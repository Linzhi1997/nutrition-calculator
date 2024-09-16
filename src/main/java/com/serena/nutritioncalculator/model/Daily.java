package com.serena.nutritioncalculator.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Daily {
    private Integer dailyId;
    private Integer userId;
    private Integer recommendCal;
    private Integer dailyCal;
    private Integer dailyCarbs;
    private Integer dailyProtein;
    private Integer dailyFat;
    private String  achievePercent;
    private Date    dailyBeginTime;
    private Date    dailyLastModifiedDate;
}
