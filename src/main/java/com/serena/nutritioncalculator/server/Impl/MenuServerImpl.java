package com.serena.nutritioncalculator.server.Impl;

import com.serena.nutritioncalculator.constant.MealType;
import com.serena.nutritioncalculator.dao.*;
import com.serena.nutritioncalculator.dto.MenuItem;
import com.serena.nutritioncalculator.dto.PagingQueryParams;
import com.serena.nutritioncalculator.dto.TimeQueryParams;
import com.serena.nutritioncalculator.model.*;
import com.serena.nutritioncalculator.server.MenuServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
public class MenuServerImpl implements MenuServer {
    private static final Logger log = LoggerFactory.getLogger(MenuServerImpl.class);
    @Autowired
    UserDao userDao;
    @Autowired
    ProfileDao profileDao;
    @Autowired
    MenuDao menuDao;
    @Autowired
    FoodDao foodDao;
    @Autowired
    DailyDao dailyDao;

    @Override
    public Integer createMenu(Integer userId,MenuItem menuItem) {

        checkUserExist(userId);
        checkFoodExist(menuItem.getFoodId());
        if (profileDao.getLastProfileByUserId(userId)==null){
            log.warn("user:{} 未建立基本資料",userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return menuDao.createMenu(userId,menuItem);
    }

    @Override
    public Menu getMenuById(Integer menuId) {
        checkMenuExist(menuId);
        return menuDao.getMenuById(menuId);
    }

    @Override
    public List<Menu> getMenus(Integer userId, TimeQueryParams timeQueryParams, PagingQueryParams pagingQueryParams) {
        checkUserExist(userId);
        return menuDao.getMenus(userId,timeQueryParams, pagingQueryParams);
    }

    @Override
    public Integer countMenu(Integer userId, TimeQueryParams timeQueryParams) {
        checkUserExist(userId);
        return menuDao.countMenu(userId,timeQueryParams);
    }

    @Override
    public void updateMenu(Integer menuId, MenuItem menuItem) {
        checkMenuExist(menuId);
        checkFoodExist(menuItem.getFoodId());
        menuDao.updateMenu(menuId, menuItem);
    }

    @Override
    public void deleteMenu(Integer menuId) {
        checkMenuExist(menuId);
        menuDao.deleteMenu(menuId);
    }

    // 確認user food menu是否存在
    private void checkUserExist(Integer userId) {
        if (userDao.getUserById(userId) == null) {
            log.warn("user: {} 不存在", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
    private void checkMenuExist(Integer menuId) {
        if (menuDao.getMenuById(menuId)== null) {
            log.warn("menu: {} 不存在", menuId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
    private void checkFoodExist(Integer foodId) {
        if (foodDao.getFoodById(foodId) == null) {
            log.warn("food: {} 不存在", foodId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}
