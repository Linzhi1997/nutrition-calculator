package com.serena.nutritioncalculator.mapper;


import com.serena.nutritioncalculator.constant.FoodLocation;
import com.serena.nutritioncalculator.constant.MealType;
import com.serena.nutritioncalculator.model.Menu;
import org.springframework.jdbc.core.RowMapper;

import java.sql.*;

public class MenuRowMapper implements RowMapper<Menu> {
    @Override
    public Menu mapRow(ResultSet rs, int rowNum) throws SQLException {
        Menu menu = new Menu();
        menu.setMenuId(rs.getInt("menu_id"));
        menu.setUserId(rs.getInt("user_id"));
        menu.setMealType(MealType.valueOf(rs.getString("meal_type")));
        menu.setFoodId(rs.getInt("food_id"));
        menu.setExchange(rs.getInt("exchange"));
        menu.setLastModifiedDate(rs.getDate("last_modified_date"));
        // 擴充 food
        menu.setFoodName(rs.getString("food_name"));
        menu.setFoodCal(rs.getInt("food_cal"));
        menu.setFoodProtein(rs.getInt("food_protein"));
        menu.setFoodFat(rs.getInt("food_fat"));
        menu.setFoodCarbs(rs.getInt("food_carbs"));
        menu.setFoodLocation(FoodLocation.valueOf(rs.getString("food_location")));
        return menu;
    }
}
