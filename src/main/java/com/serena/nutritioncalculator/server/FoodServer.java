package com.serena.nutritioncalculator.server;



import com.serena.nutritioncalculator.dto.FoodQueryParams;
import com.serena.nutritioncalculator.model.Food;

import java.util.List;


public interface FoodServer {
    Food getFoodById(Integer foodId);
    List<Food> getFoods(FoodQueryParams foodQueryParams);
    Integer getTotalResult(FoodQueryParams foodQueryParams);
}
