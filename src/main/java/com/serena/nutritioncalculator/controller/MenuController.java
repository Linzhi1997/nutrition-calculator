package com.serena.nutritioncalculator.controller;

import com.serena.nutritioncalculator.dto.MenuItem;
import com.serena.nutritioncalculator.dto.PagingQueryParams;
import com.serena.nutritioncalculator.dto.TimeQueryParams;
import com.serena.nutritioncalculator.model.Menu;
import com.serena.nutritioncalculator.server.DailyServer;
import com.serena.nutritioncalculator.server.MenuServer;
import com.serena.nutritioncalculator.util.Page;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class MenuController {

    @Autowired
    MenuServer menuServer;
    @Autowired
    DailyServer dailyServer;

    // 創建一筆飲食紀錄
    @PostMapping("/users/{userId}/menus")
    public ResponseEntity<Menu> createMenu(@PathVariable Integer userId,
                                           @RequestBody @Valid MenuItem menuItem){
        Integer menuId = menuServer.createMenu(userId, menuItem);
        Menu menu = menuServer.getMenuById(menuId);
        return ResponseEntity.status(HttpStatus.CREATED).body(menu);
    }

    // 更新一筆飲食紀錄
    @PutMapping("/menus/{menuId}")
    public ResponseEntity<Menu> updateMenu(@PathVariable Integer menuId,
                                           @RequestBody MenuItem menuItem){
        menuServer.updateMenu(menuId, menuItem);
        Menu menu = menuServer.getMenuById(menuId);
        return ResponseEntity.status(HttpStatus.CREATED).body(menu);
    }

    // 刪除一筆飲食紀錄
    @DeleteMapping("/menus/{menuId}")
    public ResponseEntity<Menu> deleteMenu(@PathVariable Integer menuId){
        menuServer.deleteMenu(menuId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 查詢個人飲食菜單 (預設為全部)
    @GetMapping("/users/{userId}/menus")
    public ResponseEntity<Page<Menu>> getMenus(@PathVariable Integer userId,
                                               // 搜尋區間
                                               @RequestParam (required = false)
                                               @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  Date beginDate,
                                               @RequestParam (required = false)
                                               @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  Date endDate,
                                               // 分頁
                                               @RequestParam(defaultValue = "10") Integer limit,
                                               @RequestParam(defaultValue = "0" ) Integer offset
                                              ) {
        // TimeQueryParams內設定時間
        TimeQueryParams timeQueryParams  = new TimeQueryParams(beginDate,endDate);
        // 設定分頁
        PagingQueryParams pagingQueryParams = new PagingQueryParams();
        pagingQueryParams.setLimit(limit);
        pagingQueryParams.setOffset(offset);
        // 設定返回值
        List<Menu> menuList = menuServer.getMenus(userId,timeQueryParams, pagingQueryParams);
        Page<Menu> menuPage = new Page<>();
        menuPage.setLimit(limit);
        menuPage.setOffset(offset);
        menuPage.setTotal(menuServer.countMenu(userId,timeQueryParams));
        menuPage.setResultsList(menuList);

        return ResponseEntity.status(HttpStatus.OK).body(menuPage);
    }

}
