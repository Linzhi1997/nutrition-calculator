package com.serena.nutritioncalculator.dao.Impl;

import com.serena.nutritioncalculator.dao.*;
import com.serena.nutritioncalculator.dto.MenuItem;
import com.serena.nutritioncalculator.dto.PagingQueryParams;
import com.serena.nutritioncalculator.dto.TimeQueryParams;
import com.serena.nutritioncalculator.mapper.MenuRowMapper;
import com.serena.nutritioncalculator.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MenuDaoImpl implements MenuDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    FoodDao foodDao;
    @Autowired
    UserDao userDao;

    @Override
    public Integer createMenu(Integer userId,MenuItem menuItem) {
        String sql = " INSERT INTO menu (user_id,meal_type,food_id,exchange,last_modified_date)" +
                    " VALUES(:userId,:mealType,:foodId,:exchange,:lastModifiedDate)";
        Map<String, Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("mealType",  menuItem.getMealType().toString());
        map.put("foodId",  menuItem.getFoodId());
        map.put("exchange",  menuItem.getExchange());
        map.put("lastModifiedDate", new Date());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map), keyHolder);
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

    @Override
    public void updateMenu(Integer menuId, MenuItem menuItem) {
        String sql = "UPDATE menu SET ";
        Map<String, Object> map = new HashMap<>();
        List<String> updateFields = new ArrayList<>();

        // 選擇性添加更新的字段
        if (menuItem.getMealType() != null) {
            updateFields.add("meal_type = :mealType");
            map.put("mealType", menuItem.getMealType().toString());
        }
        if (menuItem.getFoodId() != null) {
            updateFields.add("food_id = :foodId");
            map.put("foodId", menuItem.getFoodId());
        }
        if (menuItem.getExchange() != null) {
            updateFields.add("exchange = :exchange");
            map.put("exchange", menuItem.getExchange());
        }

        // 添加最後修改時間
        updateFields.add("last_modified_date = :lastModifiedDate");
        map.put("lastModifiedDate", new Date());

        // 最終SQL
        sql += String.join(", ", updateFields);  // 將字段用逗號連接起來
        sql += " WHERE menu_id = :menuId";
        map.put("menuId", menuId);

        namedParameterJdbcTemplate.update(sql, map);
    }

    public Menu getMenuById(Integer menuId) {
        // 查詢 menu + foods
        String sql = buildMenuQuery() + " WHERE menu_id = :menuId ";
        Map<String,Object> map = new HashMap<>();
        map.put("menuId",menuId);
        List<Menu> menuList = namedParameterJdbcTemplate.query(sql,map,new MenuRowMapper());
        if(menuList.size()>0){
            return menuList.get(0);
        }else {
            return null;
        }
    }


    public List<Menu> getMenus(Integer userId, TimeQueryParams timeQueryParams, PagingQueryParams pagingQueryParams) {
        // 查詢 menu + foods
        String sql = buildMenuQuery() +" WHERE 1=1 ";
        Map<String,Object> map = new HashMap<>();
        // 以使用者和時間來搜尋
        sql = addFilterQuery(sql,map,userId, timeQueryParams);
        // 排序（固定）
        sql = sql + " ORDER BY last_modified_date DESC ";
        // 分頁
        sql = sql + " LIMIT :limit OFFSET :offset ";
        map.put("limit", pagingQueryParams.getLimit());
        map.put("offset", pagingQueryParams.getOffset());
        List<Menu> menuList = namedParameterJdbcTemplate.query(sql,map,new MenuRowMapper());

        return menuList;
    }

    public Integer countMenu(Integer userId, TimeQueryParams timeQueryParams){
        String sql = " SELECT count(*) FROM menu as m WHERE 1=1 ";
        Map<String,Object> map = new HashMap<>();
        // 以使用者和時間來搜尋
        sql= addFilterQuery(sql,map,userId, timeQueryParams);

        Integer totalResult = namedParameterJdbcTemplate.queryForObject(sql,map,Integer.class);

        return totalResult;
    }

    private String buildMenuQuery() {
        return "SELECT m.menu_id, m.user_id, m.meal_type, m.food_id, m.exchange, m.last_modified_date, " +
                "f.food_name, " +
                "(f.food_cal * m.exchange) AS food_cal, " +
                "(f.food_carbs * m.exchange) AS food_carbs, " +
                "(f.food_protein * m.exchange) AS food_protein, " +
                "(f.food_fat * m.exchange) AS food_fat, f.food_location " +
                "FROM menu AS m LEFT JOIN food AS f " +
                "ON m.food_id = f.food_id ";
    }

    private String addFilterQuery(String sql, Map<String, Object> map, Integer userId, TimeQueryParams timeQueryParams) {
        if (userId != null) {
            sql += " AND m.user_id = :userId ";
            map.put("userId", userId);
        }
        if (timeQueryParams != null) {
            if (timeQueryParams.getBeginTime() != null) {
                sql += " AND m.last_modified_date >= :beginTime ";
                map.put("beginTime", timeQueryParams.getBeginTime());
            }
            if (timeQueryParams.getEndTime() != null) {
                sql += " AND m.last_modified_date <= :endTime ";
                map.put("endTime", timeQueryParams.getEndTime());
            }
        }
        return sql;
    }

}
