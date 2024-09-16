package com.serena.nutritioncalculator.dto;

import com.serena.nutritioncalculator.constant.FoodLocation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodQueryParams {
    private Integer         limit;
    private Integer         offset;
    private String          orderBy;
    private String          sort;
    private FoodLocation    foodLocation;
    private String          search;
    private String          compare;
    private Integer         foodCal;
    private Integer         foodProtein;
}
