package com.serena.nutritioncalculator.server;

import com.serena.nutritioncalculator.dto.MenuItem;
import com.serena.nutritioncalculator.dto.TimeQueryParams;
import com.serena.nutritioncalculator.model.Menu;

import java.util.List;

public interface MenuServer {
    Integer createMenu(Integer userId,MenuItem menuItem);
    Menu getMenuById(Integer menuId);
    List<Menu> getMenus(Integer userId, TimeQueryParams timeQueryParams);
    Integer countMenu(Integer userId, TimeQueryParams timeQueryParams);
    void updateMenu(Integer menuId, MenuItem menuItem);
    void deleteMenu(Integer menuId);
}
