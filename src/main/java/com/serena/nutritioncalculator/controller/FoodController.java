package com.serena.nutritioncalculator.controller;

import com.serena.nutritioncalculator.util.*;
import com.serena.nutritioncalculator.constant.FoodLocation;
import com.serena.nutritioncalculator.dto.FoodQueryParams;
import com.serena.nutritioncalculator.model.Food;
import com.serena.nutritioncalculator.server.FoodServer;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FoodController {
    @Autowired
    FoodServer foodServer;

    // 獲取單一食材
    @GetMapping("/foods/{foodId}")
    public ResponseEntity<Food> getFoodById(@PathVariable Integer foodId){
        Food food = foodServer.getFoodById(foodId);
        return ResponseEntity.status(HttpStatus.OK).body(food);
    }
    // 獲取所有系統食材表 & 搜尋系統食材表
    @GetMapping("/foods")
    public ResponseEntity<Page<Food>> getFoods( // 分頁
                                                @RequestParam (defaultValue = "5") Integer limit,
                                                @RequestParam (defaultValue = "0") Integer offset,
                                                // 排序
                                                @RequestParam (defaultValue = "food_location") String orderBy,
                                                @RequestParam (defaultValue = "ASC") String sort,
                                                // 搜尋
                                                @RequestParam (required = false) FoodLocation foodLocation,
                                                @RequestParam (required = false) String search,
                                                @RequestParam (required = false) String compare,
                                                @RequestParam (required = false) Integer foodCal,
                                                @RequestParam (required = false) Integer foodProtein){
        FoodQueryParams foodQueryParams = new FoodQueryParams();
        foodQueryParams.setLimit(limit);
        foodQueryParams.setOffset(offset);
        foodQueryParams.setOrderBy(orderBy);
        foodQueryParams.setSort(sort);
        foodQueryParams.setFoodLocation(foodLocation);
        foodQueryParams.setSearch(search);
        foodQueryParams.setCompare(compare);
        foodQueryParams.setFoodCal(foodCal);
        foodQueryParams.setFoodProtein(foodProtein);

        Page<Food> foodPage = new Page<>();

        List<Food> foodList = foodServer.getFoods(foodQueryParams);
        foodPage.setLimit(limit);
        foodPage.setOffset(offset);
        foodPage.setTotal(foodServer.getTotalResult(foodQueryParams));
        foodPage.setResultsList(foodList);

        return  ResponseEntity.status(HttpStatus.OK).body(foodPage);

    }
}

