package com.serena.nutritioncalculator.model;

import com.serena.nutritioncalculator.constant.FoodLocation;
import com.serena.nutritioncalculator.constant.MealType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Menu {
    private Integer         menuId;
    private Integer         userId;
    private MealType        mealType;
    private Integer         exchange;
    private Integer         foodId;
    private Date            lastModifiedDate;
    // 擴充 food
    private String          foodName;
    private Integer         foodCal;
    private Integer         foodProtein;
    private Integer         foodFat;
    private Integer         foodCarbs;
    private FoodLocation    foodLocation;
}
