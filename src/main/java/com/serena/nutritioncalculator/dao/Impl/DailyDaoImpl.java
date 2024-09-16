package com.serena.nutritioncalculator.dao.Impl;

import com.serena.nutritioncalculator.dao.DailyDao;
import com.serena.nutritioncalculator.dto.DailyParams;
import com.serena.nutritioncalculator.dto.PagingQueryParams;
import com.serena.nutritioncalculator.dto.TimeQueryParams;
import com.serena.nutritioncalculator.mapper.DailyRowMapper;
import com.serena.nutritioncalculator.model.Daily;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DailyDaoImpl implements DailyDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createDailyRecordForToday(Integer userId,Date dailyBeginTime,DailyParams dailyParams) {
        String sql = " INSERT INTO daily (user_id,recommend_cal,daily_cal,daily_carbs,daily_protein,daily_fat,achieve_percent,daily_begin_time,daily_last_modified_date)" +
                " VALUES(:userId,:recommendCal,:dailyCal,:dailyCarbs,:dailyProtein,:dailyFat,:achievePercent,:dailyBeginTime,:dailyLastModifiedDate)";

        Map<String, Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("dailyBeginTime",dailyBeginTime);
        map = addParamsMap(map,dailyParams);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
        int dailyId = keyHolder.getKey().intValue();

        return dailyId;
    }

    @Override
    public void updateDailyRecordForToday(Integer dailyId, DailyParams dailyParams) {
        String sql = " UPDATE daily SET recommend_cal=:recommendCal,daily_cal=:dailyCal, " +
                "daily_carbs=:dailyCarbs,daily_protein=:dailyProtein,daily_fat=:dailyFat, " +
                "achieve_percent=:achievePercent,daily_last_modified_date=:dailyLastModifiedDate " +
                "WHERE daily_id=:dailyId";

        Map<String, Object> map = new HashMap<>();
        map.put("dailyId",dailyId);
        map = addParamsMap(map,dailyParams);

        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public Daily getDailyById(Integer dailyId) {
        String sql = "SELECT daily_id,user_id,recommend_cal,daily_cal,daily_carbs,daily_protein,daily_fat," +
                "achieve_percent,daily_begin_time,daily_last_modified_date " +
                "FROM daily WHERE daily_id=:dailyId ";
        Map<String,Object> map = new HashMap<>();
        map.put("dailyId",dailyId);
        List<Daily> dailyList = namedParameterJdbcTemplate.query(sql,map,new DailyRowMapper());
        if(dailyList.size()!=0){
            return dailyList.get(0);
        }else {
            return null;
        }
    }

    @Override
    public List<Daily> getDailyRecords(Integer userId, TimeQueryParams timeQueryParams, PagingQueryParams pagingQueryParams) {
        String sql = "SELECT daily_id,user_id,recommend_cal,daily_cal,daily_carbs,daily_protein,daily_fat,achieve_percent,daily_begin_time,daily_last_modified_date FROM daily WHERE 1=1 ";
        Map<String,Object> map = new HashMap<>();
        sql = addFilterQuery(sql,map,userId,timeQueryParams);
        // 以使用者和時間來搜尋
        sql = addFilterQuery(sql,map,userId,timeQueryParams);
        // 排序（固定）
        sql = sql + " ORDER BY daily_begin_time DESC ";
        // 分頁
        sql = sql + " LIMIT :limit OFFSET :offset ";
        map.put("limit", pagingQueryParams.getLimit());
        map.put("offset", pagingQueryParams.getOffset());

        List<Daily> dailyList = namedParameterJdbcTemplate.query(sql,map,new DailyRowMapper());
        if(dailyList!=null){
            return dailyList;
        }else {
            return new ArrayList<>();
        }
    }

    @Override
    public Integer countDailyRecords(Integer userId, TimeQueryParams timeQueryParams) {
        String sql = "SELECT count(*) FROM daily WHERE 1=1 ";
        Map<String,Object> map = new HashMap<>();
        // 以使用者和時間來搜尋
        sql = addFilterQuery(sql,map,userId,timeQueryParams);

        Integer totalResult = namedParameterJdbcTemplate.queryForObject(sql,map,Integer.class);
        return totalResult;
    }

    private Map<String, Object> addParamsMap(Map<String, Object> map,DailyParams dailyParams) {
        map.put("recommendCal", dailyParams.getRecommendCal());
        map.put("dailyCal", dailyParams.getDailyCal());
        map.put("dailyCarbs", dailyParams.getDailyCarbs());
        map.put("dailyProtein", dailyParams.getDailyProtein());
        map.put("dailyFat", dailyParams.getDailyFat());
        map.put("achievePercent", dailyParams.getAchievePercent());
        map.put("dailyLastModifiedDate", new Date());
        return map;
    }

    private String addFilterQuery(String sql, Map<String, Object> map, Integer userId, TimeQueryParams timeQueryParams) {
        if (userId != null) {
            sql += " AND user_id = :userId ";
            map.put("userId", userId);
        }
        if (timeQueryParams != null) {
            if (timeQueryParams.getBeginTime() != null) {
                sql += " AND daily_last_modified_date >= :beginTime ";
                map.put("beginTime", timeQueryParams.getBeginTime());
            }
            if (timeQueryParams.getEndTime() != null) {
                sql  += " AND daily_last_modified_date < :endTime "; // 使用 < 而不是 <=
                map.put("endTime", timeQueryParams.getEndTime());
            }
        }
        return sql;
    }
}
