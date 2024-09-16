package com.serena.nutritioncalculator.server.Impl;

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

        if(foodDao.getFoodById(menuItem.getFoodId())==null){
            log.warn("food:{} 不存在於資料庫",menuItem.getFoodId());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return menuDao.createMenu(userId,menuItem);
    }

    @Override
    public Menu getMenuById(Integer menuId) {
        if(menuId==null){
            log.warn("menu: {} 不存在", menuId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return menuDao.getMenuById(menuId);
    }

    @Override
    public List<Menu> getMenus(Integer userId, TimeQueryParams timeQueryParams, PagingQueryParams pagingQueryParams) {
        return menuDao.getMenus(userId,timeQueryParams, pagingQueryParams);
    }

    @Override
    public Integer countMenu(Integer userId, TimeQueryParams timeQueryParams) {
        return menuDao.countMenu(userId,timeQueryParams);
    }

    @Override
    public void updateMenu(Integer menuId, MenuItem menuItem) {
        if(menuId==null){
            log.warn("menu: {} 不存在", menuId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        menuDao.updateMenu(menuId, menuItem);
    }

    @Override
    public void deleteMenu(Integer menuId) {
        if(getMenuById(menuId)==null){
            log.warn("MenuId: {} 不存在", menuId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        menuDao.deleteMenu(menuId);
    }

}
