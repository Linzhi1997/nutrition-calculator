package com.serena.nutritioncalculator.mapper;

import com.serena.nutritioncalculator.model.Daily;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DailyRowMapper implements RowMapper<Daily> {
    @Override
    public Daily mapRow(ResultSet rs, int rowNum) throws SQLException {
        Daily daily = new Daily();
        daily.setDailyId(rs.getInt("daily_id"));
        daily.setUserId(rs.getInt("user_id"));
        daily.setRecommendCal(rs.getInt("recommend_cal"));
        daily.setDailyCal(rs.getInt("daily_cal"));
        daily.setDailyCarbs(rs.getInt("daily_carbs"));
        daily.setDailyProtein(rs.getInt("daily_protein"));
        daily.setDailyFat(rs.getInt("daily_fat"));
        daily.setAchievePercent(rs.getString("achieve_percent"));
        daily.setDailyBeginTime(rs.getDate("daily_begin_time"));
        daily.setDailyLastModifiedDate(rs.getDate("daily_last_modified_date"));
        return daily;
    }
}
