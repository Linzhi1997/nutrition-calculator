package com.serena.nutritioncalculator.mapper;



import com.serena.nutritioncalculator.constant.FoodLocation;
import com.serena.nutritioncalculator.model.Food;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FoodRowmapper implements RowMapper<Food> {

    @Override
    public Food mapRow(ResultSet rs, int rowNum) throws SQLException {
        Food food = new Food();
        food.setFoodId(rs.getInt("food_id"));
        food.setFoodName(rs.getString("food_name"));
        food.setFoodCal(rs.getInt("food_cal"));
        food.setFoodProtein(rs.getInt("food_protein"));
        food.setFoodFat(rs.getInt("food_fat"));
        food.setFoodCarbs(rs.getInt("food_carbs"));
        food.setFoodLocation(FoodLocation.valueOf(rs.getString("food_location")));
        return food;
    }
}
