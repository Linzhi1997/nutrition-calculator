package com.serena.nutritioncalculator.dao;


import com.serena.nutritioncalculator.dto.MenuRequest;
import com.serena.nutritioncalculator.dto.MenuQueryParams;
import com.serena.nutritioncalculator.model.Menu;
import com.serena.nutritioncalculator.model.MenuFood;

import java.util.List;

public interface MenuDao {
    Integer createMenu(Integer usersId, MenuRequest menuRequest);
    void deleteMenu(Integer menuId);
    void updateMenu(Integer menuId, MenuRequest menuRequest);
    Menu getMenuById(Integer menuId);
    List<MenuFood> getMenuFoods(MenuQueryParams menuQueryParams);
    Integer getMenuTotal(MenuQueryParams menuQueryParams);
}
