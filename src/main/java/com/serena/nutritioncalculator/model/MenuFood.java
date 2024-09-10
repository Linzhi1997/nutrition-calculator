package com.serena.nutritioncalculator.model;


import com.serena.nutritioncalculator.constant.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class MenuFood {
    private Integer     userId;
    private MealType    mealType;
    private Integer     exchange;
    private Date        menuCreatedTime;
    private String      foodName;
    private Integer     foodCal;
    private Integer     foodProtein;
    private Integer     foodFat;
    private Integer     foodCarbs;
    private FoodLocation foodLocation;
}
