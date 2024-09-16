package com.serena.nutritioncalculator.dao;


import com.serena.nutritioncalculator.dto.MenuItem;
import com.serena.nutritioncalculator.dto.PagingQueryParams;
import com.serena.nutritioncalculator.dto.TimeQueryParams;
import com.serena.nutritioncalculator.model.Menu;

import java.util.List;

public interface MenuDao {
    Integer createMenu(Integer userId,MenuItem menuItem);
    Menu getMenuById(Integer menuId);
    List<Menu> getMenus(Integer userId, TimeQueryParams timeQueryParams, PagingQueryParams pagingQueryParams);
    Integer countMenu(Integer userId, TimeQueryParams timeQueryParams);
    void updateMenu(Integer menuId, MenuItem menuItem);
    void deleteMenu(Integer menuId);
}
