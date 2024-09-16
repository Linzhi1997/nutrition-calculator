package com.serena.nutritioncalculator.server;

import com.serena.nutritioncalculator.dto.DailyCreateRequest;
import com.serena.nutritioncalculator.model.Daily;

public interface DailyServer {
    Integer createDailyRecordForToday(Integer userId, DailyCreateRequest dailyCreateRequest);
    Daily getDailyById(Integer dailyId);
}
