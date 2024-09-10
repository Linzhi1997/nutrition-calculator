package com.serena.nutritioncalculator.dao;


import com.serena.nutritioncalculator.dto.MenuCreateRequest;
import com.serena.nutritioncalculator.dto.MenuQueryParams;
import com.serena.nutritioncalculator.model.Menu;
import com.serena.nutritioncalculator.model.MenuFood;

import java.util.List;

public interface MenuDao {
    Integer createMenu(MenuCreateRequest menuCreateRequest);
    void deleteMenu(Integer menuId);
    Menu getMenuById(Integer menuId);
    List<MenuFood> getMenuFoods(MenuQueryParams menuQueryParams);
    Integer getMenuTotal(MenuQueryParams menuQueryParams);
}
