package com.serena.nutritioncalculator.controller;

import com.serena.nutritioncalculator.dto.PagingQueryParams;
import com.serena.nutritioncalculator.dto.TimeQueryParams;
import com.serena.nutritioncalculator.model.Daily;
import com.serena.nutritioncalculator.server.DailyServer;
import com.serena.nutritioncalculator.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@EnableCaching
public class DailyController {
    @Autowired
    DailyServer dailyServer;

    // 顯示時 自動更新
    @GetMapping("/users/{userId}/dailys/today")
    @Cacheable(value = "dailyRecordCache", key = "#userId")
    public ResponseEntity<Daily> showDailyRecordForToday(@PathVariable Integer userId,
                                                        @RequestParam(required = false) Integer recommendCal) {
        // 更新今日統計表
        Integer dailyId = dailyServer.updateDailyRecordForToday(userId, recommendCal);
        // 獲取今日統計表
        Daily daily = dailyServer.getDailyById(dailyId);
        return ResponseEntity.status(HttpStatus.CREATED).body(daily);
    }

    @GetMapping("/users/{userId}/dailys")
    public ResponseEntity<Page<Daily>> getHistoryDailyRecord(@PathVariable Integer userId,
                                                             // 搜尋區間
                                                             @RequestParam (required = false)
                                                             @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  Date beginDate,
                                                             @RequestParam (required = false)
                                                             @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  Date endDate,
                                                             // 分頁
                                                             @RequestParam(defaultValue = "10") Integer limit,
                                                             @RequestParam(defaultValue = "0" ) Integer offset
    ){
        // TimeQueryParams內設定時間
        TimeQueryParams timeQueryParams  = new TimeQueryParams(beginDate,endDate);
        // 設定分頁
        PagingQueryParams pagingQueryParams = new PagingQueryParams();
        pagingQueryParams.setLimit(limit);
        pagingQueryParams.setOffset(offset);
        // 設定返回值
        List<Daily> dailyList = dailyServer.getDailyRecords(userId,timeQueryParams, pagingQueryParams);
        Page<Daily> dailyPage= new Page<>();
        dailyPage.setLimit(limit);
        dailyPage.setOffset(offset);
        dailyPage.setTotal(dailyServer.countDailyRecords(userId,timeQueryParams));
        dailyPage.setResultsList(dailyList);

        return ResponseEntity.status(HttpStatus.OK).body(dailyPage);
    }

}
