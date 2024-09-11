package com.serena.nutritioncalculator.mapper;



import com.serena.nutritioncalculator.constant.FoodLocation;
import com.serena.nutritioncalculator.constant.MealType;
import com.serena.nutritioncalculator.model.MenuFood;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MenuFoodRowmapper implements RowMapper<MenuFood> {

    @Override
    public MenuFood mapRow(ResultSet rs, int rowNum) throws SQLException {
        MenuFood menuFood = new MenuFood();
        menuFood.setUserId(rs.getInt("m.user_id"));
        menuFood.setMealType(MealType.valueOf(rs.getString("m.meal_type")));
        menuFood.setExchange(rs.getInt("m.exchange"));
        menuFood.setMenuCreatedDate(rs.getTimestamp("m.menu_created_date"));
        menuFood.setFoodName(rs.getString("f.food_name"));
        menuFood.setFoodCal(rs.getInt("f.food_cal"));
        menuFood.setFoodProtein(rs.getInt("f.food_protein"));
        menuFood.setFoodFat(rs.getInt("f.food_fat"));
        menuFood.setFoodCarbs(rs.getInt("f.food_carbs"));
        menuFood.setFoodLocation(FoodLocation.valueOf(rs.getString("f.food_location")));
        return menuFood;
    }
}
