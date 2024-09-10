package com.serena.nutritioncalculator.model;

import com.serena.nutritioncalculator.constant.FoodLocation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Food {
    private Integer     foodId;
    private String      foodName;
    private Integer     foodCal;
    private Integer     foodProtein;
    private Integer     foodFat;
    private Integer     foodCarbs;
    private FoodLocation foodLocation;
}
