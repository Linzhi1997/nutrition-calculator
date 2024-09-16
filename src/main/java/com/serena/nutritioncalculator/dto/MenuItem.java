package com.serena.nutritioncalculator.dto;


import com.serena.nutritioncalculator.constant.MealType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuItem {
    @NotNull
    private MealType    mealType;
    @NotNull
    private Integer     foodId;
    @NotNull
    private Integer     exchange;
}
