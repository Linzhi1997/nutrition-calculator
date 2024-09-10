package com.serena.nutritioncalculator.controller;

import com.serena.nutritioncalculator.dto.MenuCreateRequest;
import com.serena.nutritioncalculator.dto.MenuQueryParams;
import com.serena.nutritioncalculator.model.Menu;
import com.serena.nutritioncalculator.model.MenuFood;
import com.serena.nutritioncalculator.server.MenuServer;
import com.serena.nutritioncalculator.util.DailyPage;
import com.serena.nutritioncalculator.util.Page;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
public class MenuController {

    @Autowired
    MenuServer menuServer;

    //創造一筆飲食紀錄
    @PostMapping("/menus")
    public ResponseEntity<Menu> createMenu(@RequestBody @Valid MenuCreateRequest menuCreateRequest) {
        Integer menuId = menuServer.createMenu(menuCreateRequest);
        Menu menu = menuServer.getMenuById(menuId);
        return ResponseEntity.status(HttpStatus.CREATED).body(menu);
    }

    @DeleteMapping("/menus/{menuId}")
    public ResponseEntity deleteMenu(@PathVariable Integer menuId){
        menuServer.deleteMenu(menuId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    // 查詢個人飲食紀錄表
    @GetMapping("/users/{usersId}/menus")
    public ResponseEntity<Page<MenuFood>> getMenus(@PathVariable Integer usersId,
                                                  // 搜尋區間
                                                  @RequestParam (required = false)  String beginDate,
                                                  @RequestParam (required = false)  String endDate,
                                                  // 使用者自主設定熱量
                                                  @RequestParam(required = false)  Integer recommendCal,
                                                  // 分頁
                                                  @RequestParam(defaultValue = "10") Integer limit,
                                                  @RequestParam(defaultValue = "0" ) Integer offset
                                                  ) throws ParseException {
        // 傳遞參數
        MenuQueryParams menuQueryParams = new MenuQueryParams();
        menuQueryParams.setUserId(usersId);
        menuQueryParams.setRecommendCal(recommendCal);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        menuQueryParams.setMenuBeginTime(dateFormat.parse(beginDate));
        menuQueryParams.setMenuEndTime(dateFormat.parse(endDate));

        // 設定返回值
        List<MenuFood> menuFoodList = menuServer.getMenuFoods(menuQueryParams);
        Page<MenuFood> menuFoodPage = new Page<>();
        menuFoodPage.setLimit(limit);
        menuFoodPage.setOffset(offset);
        menuFoodPage.setTotal(menuServer.getMenuTotal(menuQueryParams));
        menuFoodPage.setResultsList(menuFoodList);

        return ResponseEntity.status(HttpStatus.OK).body(menuFoodPage);
    }

    // 查詢個人飲食紀錄表(含每日總表)
    @GetMapping("/users/{usersId}/menus/daily")
    public ResponseEntity<DailyPage<MenuFood>> getDailyRecord(@PathVariable Integer usersId,
                                                              // 搜尋區間
                                                              @RequestParam (required = false)  String beginDate,
                                                              @RequestParam (required = false)  String endDate,
                                                              // 使用者自主設定熱量
                                                              @RequestParam(required = false)  Integer recommendCal,
                                                              // 分頁
                                                              @RequestParam(defaultValue = "10") Integer limit,
                                                              @RequestParam(defaultValue = "0" ) Integer offset
                                                             ) throws ParseException {
        // 傳遞參數
        MenuQueryParams menuQueryParams = new MenuQueryParams();
        menuQueryParams.setUserId(usersId);
        menuQueryParams.setRecommendCal(recommendCal);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        menuQueryParams.setMenuBeginTime(dateFormat.parse(beginDate));
        menuQueryParams.setMenuEndTime(dateFormat.parse(endDate));

        // 設定返回值
        List<MenuFood> menuFoodList = menuServer.getMenuFoods(menuQueryParams);
        DailyPage<MenuFood> dailyPage = new DailyPage<>();
        dailyPage.setLimit(limit);
        dailyPage.setOffset(offset);
        dailyPage.setTotal(menuServer.getMenuTotal(menuQueryParams));
        dailyPage.setDailyIntake(menuServer.getDailyIntake(menuQueryParams));
        dailyPage.setResultsList(menuFoodList);

        return ResponseEntity.status(HttpStatus.OK).body(dailyPage);
    }


}
