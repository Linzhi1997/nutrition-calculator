package com.serena.nutritioncalculator.dao.Impl;


import com.serena.nutritioncalculator.dao.FoodDao;
import com.serena.nutritioncalculator.dto.FoodQueryParams;
import com.serena.nutritioncalculator.mapper.FoodRowMapper;
import com.serena.nutritioncalculator.model.Food;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FoodDaoImpl implements FoodDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public FoodDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Food getFoodById(Integer foodId) {
        String sql = "SELECT food_id,food_name,food_cal,food_carbs,food_protein,food_fat,food_location " +
                " FROM food WHERE food_id=:foodId ";
        Map<String,Object> map = new HashMap<>();
        map.put("foodId",foodId);
        List<Food> foodList = namedParameterJdbcTemplate.query(sql,map,new FoodRowMapper());
        if(foodList.size()>0){
            return foodList.get(0);
        }else {
            return null;
        }
    }

    @Override
    public List<Food> getFoods(FoodQueryParams foodQueryParams) {
        String sql = " SELECT food_id,food_name,food_cal,food_carbs,food_protein,food_fat,food_location " +
                " FROM food WHERE 1=1 ";
        Map<String,Object> map = new HashMap<>();
        // 搜索
        sql = addFilter(sql,map,foodQueryParams);
        // 排序
        sql = sql + " ORDER BY "+foodQueryParams.getOrderBy()+" "+foodQueryParams.getSort();
        // 分頁
        sql = sql + " LIMIT :limit  OFFSET :offset";

        map.put("limit",foodQueryParams.getLimit());
        map.put("offset",foodQueryParams.getOffset());

        List<Food> foodList = namedParameterJdbcTemplate.query(sql,map,new FoodRowMapper());

        return foodList;
    }
    public Integer getTotalResult(FoodQueryParams foodQueryParams){
        String sql = " SELECT count(*) FROM food WHERE 1=1 ";
        Map<String,Object> map = new HashMap<>();
        sql=addFilter(sql,map,foodQueryParams);

        Integer totalResult = namedParameterJdbcTemplate.queryForObject(sql,map,Integer.class);

        return totalResult;
    }

    private String addFilter(String sql,Map<String,Object> map,FoodQueryParams foodQueryParams){
        // 營養素搜索
        String compare = foodQueryParams.getCompare();
        Integer foodCal = foodQueryParams.getFoodCal();
        Integer foodProtein = foodQueryParams.getFoodProtein();

        if(compare!=null && foodCal!=null) {
            sql = sql + " AND food_cal " + compare + " :foodCal";
            map.put("foodCal",foodCal);
        }
        if(compare!=null && foodProtein!=null) {
            sql = sql + " AND food_protein " + compare + " :foodProtein";
            map.put("foodProtein",foodProtein);
        }
        // 位置搜索
        if(foodQueryParams.getFoodLocation()!=null){
            sql = sql + " AND food_location LIKE :foodLocation";
            map.put("foodLocation",foodQueryParams.getFoodLocation().name());
        }
        // 名詞搜索
        if(foodQueryParams.getSearch()!=null){
            sql = sql + " AND food_name LIKE :search";
            map.put("search","%"+foodQueryParams.getSearch()+"%");
        }

        return sql;
    }
}
