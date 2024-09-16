package com.serena.nutritioncalculator.dao;

import com.serena.nutritioncalculator.dto.DailyParams;
import com.serena.nutritioncalculator.dto.TimeQueryParams;
import com.serena.nutritioncalculator.model.Daily;

import java.util.Date;
import java.util.List;

public interface DailyDao {
    Integer createDailyRecordForToday(Integer userId, Date dailyBeginTime, DailyParams dailyParams);
    Daily getDailyById(Integer dailyId);
    List<Daily> getDailyRecords(Integer userId, TimeQueryParams timeQueryParams);
    void updateDailyRecordForToday(Integer dailyId,DailyParams dailyParams);
}
