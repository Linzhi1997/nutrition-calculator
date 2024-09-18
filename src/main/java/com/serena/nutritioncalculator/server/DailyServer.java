package com.serena.nutritioncalculator.server;

import com.serena.nutritioncalculator.dto.PagingQueryParams;
import com.serena.nutritioncalculator.dto.TimeQueryParams;
import com.serena.nutritioncalculator.model.Daily;

import java.util.List;

public interface DailyServer {
    Integer updateDailyRecordForToday(Integer userId,Integer recommendCal);
    Daily getDailyById(Integer dailyId);
    List<Daily> getDailyRecords(Integer userId, TimeQueryParams timeQueryParams, PagingQueryParams pagingQueryParams);
    Integer countDailyRecords(Integer userId, TimeQueryParams timeQueryParams);
}
