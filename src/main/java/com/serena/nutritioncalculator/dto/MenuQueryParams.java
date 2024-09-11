package com.serena.nutritioncalculator.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MenuQueryParams {
    @NotNull
    private Integer userId;
    private Integer recommendCal;
    private Date    menuBeginTime;
    private Date    menuEndTime;
}
