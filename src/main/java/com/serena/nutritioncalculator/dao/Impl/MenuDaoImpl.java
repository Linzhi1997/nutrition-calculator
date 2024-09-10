package com.serena.nutritioncalculator.dao.Impl;

import com.serena.nutritioncalculator.dao.*;
import com.serena.nutritioncalculator.dto.MenuCreateRequest;
import com.serena.nutritioncalculator.dto.MenuQueryParams;
import com.serena.nutritioncalculator.mapper.MenuFoodRowmapper;
import com.serena.nutritioncalculator.mapper.MenuRowmapper;
import com.serena.nutritioncalculator.model.Menu;
import com.serena.nutritioncalculator.model.MenuFood;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MenuDaoImpl implements MenuDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    FoodDao foodDao;
    @Autowired
    UserDao userDao;

    @Override
    public Integer createMenu(MenuCreateRequest menuCreateRequest) {
        String sql = "INSERT INTO menu " +
                " (user_id,meal_type,food_id,exchange,menu_created_time)" +
                " VALUES(:userId,:mealType,:foodId,:exchange,:menuCreatedTime)";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", menuCreateRequest.getUserId());
        map.put("mealType", menuCreateRequest.getMealType().toString());
        map.put("foodId", menuCreateRequest.getFoodId());
        map.put("exchange",menuCreateRequest.getExchange());
        map.put("menuCreatedTime", new Date());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
        int menuId = keyHolder.getKey().intValue();

        return menuId;
    }

    @Override
    public void deleteMenu(Integer menuId) {
        String sql = " DELETE FROM menu WHERE menu_id = :menuId ";
        Map<String,Object> map = new HashMap<>();
        map.put("menuId",menuId);
        namedParameterJdbcTemplate.update(sql,map);
    }

    public Menu getMenuById(Integer menuId) {
        String sql = "SELECT menu_id,user_id,meal_type,food_id,exchange,menu_created_time " +
                " FROM menu WHERE menu_id = :menuId ";
        Map<String,Object> map = new HashMap<>();
        map.put("menuId",menuId);
        List<Menu> menuList = namedParameterJdbcTemplate.query(sql,map,new MenuRowmapper());
        if(menuList.size()>0){
            return menuList.get(0);
        }else {
            return null;
        }
    }

    public List<MenuFood> getMenuFoods(MenuQueryParams menuQueryParams) {
        // 查詢menu對應的foods
        String sql = "SELECT m.user_id,m.meal_type,m.exchange,m.menu_created_time,f.food_name,f.food_cal,f.food_carbs,f.food_protein,f.food_fat,f.food_location " +
                "FROM food as f LEFT JOIN menu as m ON m.food_id = f.food_id " +
                "WHERE 1=1 ";
        Map<String,Object> map = new HashMap<>();
        sql = addTimeFilter(sql,map,menuQueryParams);

        List<MenuFood> menuFoodList = namedParameterJdbcTemplate.query(sql,map,new MenuFoodRowmapper());

        return menuFoodList;
    }

    public Integer getMenuTotal(MenuQueryParams menuQueryParams){
        String sql = " SELECT count(*) FROM menu as m WHERE 1=1 ";
        Map<String,Object> map = new HashMap<>();
        sql= addTimeFilter(sql,map,menuQueryParams);

        Integer totalResult = namedParameterJdbcTemplate.queryForObject(sql,map,Integer.class);

        return totalResult;
    }

    private String addTimeFilter(String sql, Map<String,Object> map, MenuQueryParams menuQueryParams){

        if(menuQueryParams.getUserId()!=null&&menuQueryParams.getMenuBeginTime()!=null&&menuQueryParams.getMenuEndTime()!=null) {
            sql = sql + " AND m.user_id =:userId AND m.menu_created_time BETWEEN :menuBeginTime AND :menuEndTime;";
        }
        map.put("userId",menuQueryParams.getUserId());
        map.put("menuBeginTime",menuQueryParams.getMenuBeginTime());
        map.put("menuEndTime",menuQueryParams.getMenuEndTime());
        return sql;
    }

}
