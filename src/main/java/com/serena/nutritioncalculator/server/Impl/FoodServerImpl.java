package com.serena.nutritioncalculator.server.Impl;


import com.serena.nutritioncalculator.constant.FoodLocation;
import com.serena.nutritioncalculator.dao.FoodDao;
import com.serena.nutritioncalculator.dto.FoodQueryParams;
import com.serena.nutritioncalculator.model.Food;
import com.serena.nutritioncalculator.server.FoodServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.Arrays;

@Component
public class FoodServerImpl implements FoodServer {
    @Autowired
    private static final Logger log = LoggerFactory.getLogger(FoodServerImpl.class);
    @Autowired
    FoodDao foodDao;
    @Override
    public Food getFoodById(Integer foodId) {
        Food food = foodDao.getFoodById(foodId);
        if(food==null){
            log.warn("Food Id : {} 未創建",foodId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return food;
    }

    @Override
    public List<Food> getFoods(FoodQueryParams foodQueryParams) {
        Set<String> validOperator = new HashSet<>(Arrays.asList(">", "<", ">=", "<=", "="));
        if (!validOperator.contains(foodQueryParams.getCompare())){
            log.warn("food 比較符錯誤");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        List<Food> foodList = foodDao.getFoods(foodQueryParams);
        return foodList;
    }

    @Override
    public Integer getTotalResult(FoodQueryParams foodQueryParams) {
        return foodDao.getTotalResult(foodQueryParams);
    }
}
