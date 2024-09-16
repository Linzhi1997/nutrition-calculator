package com.serena.nutritioncalculator.controller;

import com.serena.nutritioncalculator.dto.DailyCreateRequest;
import com.serena.nutritioncalculator.dto.TimeQueryParams;
import com.serena.nutritioncalculator.model.Daily;
import com.serena.nutritioncalculator.model.Menu;
import com.serena.nutritioncalculator.server.DailyServer;
import com.serena.nutritioncalculator.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class DailyController {
    @Autowired
    DailyServer dailyServer;

    // 查找時自動更新
    @GetMapping("/users/{userId}/dailys/today")
    public ResponseEntity<Daily> getDailyRecordForToday(@PathVariable Integer userId,
                                                        @RequestParam(required = false)
                                                        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date beginTime,
                                                        @RequestParam(required = false) Integer recommendCal) {
        // 創建今日統計表
        DailyCreateRequest dailyCreateRequest = new DailyCreateRequest();
        dailyCreateRequest.setBeginTime(beginTime);
        dailyCreateRequest.setRecommendCal(recommendCal);
        Integer dailyId = dailyServer.createDailyRecordForToday(userId, dailyCreateRequest);
        // 獲取今日統計表
        Daily daily = dailyServer.getDailyById(dailyId);
        return ResponseEntity.status(HttpStatus.CREATED).body(daily);
    }

}
