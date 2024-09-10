package com.serena.nutritioncalculator.util;

import com.serena.nutritioncalculator.model.DailyIntake;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DailyPage<T>{
    private DailyIntake dailyIntake;
    private List<T>     resultsList;
    private Integer     limit;
    private Integer     offset;
    private Integer     total;
}
