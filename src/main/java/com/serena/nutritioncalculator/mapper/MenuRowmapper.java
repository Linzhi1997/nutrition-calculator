package com.serena.nutritioncalculator.mapper;


import com.serena.nutritioncalculator.constant.MealType;
import com.serena.nutritioncalculator.model.Menu;
import org.springframework.jdbc.core.RowMapper;

import java.sql.*;

public class MenuRowmapper implements RowMapper<Menu> {
    @Override
    public Menu mapRow(ResultSet rs, int rowNum) throws SQLException {
        Menu menu = new Menu();
        menu.setMenuId(rs.getInt("menu_id"));
        menu.setUserId(rs.getInt("user_id"));
        menu.setMealType(MealType.valueOf(rs.getString("meal_type")));
        menu.setFoodId(rs.getInt("food_id"));
        menu.setExchange(rs.getInt("exchange"));
        menu.setMenuCreatedDate(rs.getTimestamp("menu_created_date"));
        return menu;
    }
}
