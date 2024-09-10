package com.serena.nutritioncalculator.model;

import com.serena.nutritioncalculator.constant.MealType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Menu {
    private Integer menuId;
    private Integer userId;
    private MealType mealType;
    private Integer foodId;
    private Integer exchange;
    private Date    menuCreatedTime;
}
