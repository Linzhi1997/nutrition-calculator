package com.serena.nutritioncalculator.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DailyCreateRequest {

    private Date    beginTime;
    private Integer recommendCal;
}
