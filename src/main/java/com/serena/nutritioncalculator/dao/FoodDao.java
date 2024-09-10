package com.serena.nutritioncalculator.dao;



import com.serena.nutritioncalculator.dto.FoodQueryParams;
import com.serena.nutritioncalculator.model.Food;

import java.util.List;

public interface FoodDao {
    Food getFoodById(Integer foodId);
    List<Food> getFoods(FoodQueryParams foodQueryParams);
    Integer getTotalResult(FoodQueryParams foodQueryParams);
}
