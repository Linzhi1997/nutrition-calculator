package com.serena.nutritioncalculator.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MenuQueryParams {
    private Integer userId;
    private Integer recommendCal;
    private Date    menuBeginTime;
    private Date    menuEndTime;
}
