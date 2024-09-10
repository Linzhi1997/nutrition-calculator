package com.serena.nutritioncalculator.server;

import com.serena.nutritioncalculator.dto.MenuCreateRequest;
import com.serena.nutritioncalculator.dto.MenuQueryParams;
import com.serena.nutritioncalculator.model.DailyIntake;
import com.serena.nutritioncalculator.model.Menu;
import com.serena.nutritioncalculator.model.MenuFood;

import java.util.List;

public interface MenuServer {
    Integer createMenu(MenuCreateRequest menuCreateRequest);
    void deleteMenu(Integer menuId);
    Menu getMenuById(Integer menuId);
    List<MenuFood> getMenuFoods(MenuQueryParams menuQueryParams);
    Integer getMenuTotal(MenuQueryParams menuQueryParams);
    DailyIntake getDailyIntake(MenuQueryParams menuQueryParams);
}
