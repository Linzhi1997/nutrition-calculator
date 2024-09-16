package com.serena.nutritioncalculator.server.Impl;

import com.serena.nutritioncalculator.dao.*;
import com.serena.nutritioncalculator.dto.DailyParams;
import com.serena.nutritioncalculator.dto.DailyCreateRequest;
import com.serena.nutritioncalculator.dto.PagingQueryParams;
import com.serena.nutritioncalculator.dto.TimeQueryParams;
import com.serena.nutritioncalculator.model.*;
import com.serena.nutritioncalculator.server.DailyServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.util.Date;
import java.util.List;

@Component
public class DailyServerImpl implements DailyServer {
    private static final Logger log = LoggerFactory.getLogger(MenuServerImpl.class);
    @Autowired
    UserDao userDao;
    @Autowired
    ProfileDao profileDao;
    @Autowired
    MenuDao menuDao;
    @Autowired
    FoodDao foodDao;
    @Autowired
    DailyDao dailyDao;

    @Override
    public Integer createDailyRecordForToday(Integer userId, DailyCreateRequest dailyCreateRequest) {
        // 檢查 user 是否存在
        User user = userDao.getUserById(userId);
        if(user==null){
            log.warn("此user: {} 不存在", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        // 檢查基本資料是否創建
        Profile profile = profileDao.getLastProfileByUserId(userId);
        if(profile==null){
            log.warn("此user: {} 未創建基本資料", userId);
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        // 默認0點起始時間
        TimeQueryParams timeQueryParams = new TimeQueryParams();
        // 使用者設定其他起始時間
        if(dailyCreateRequest.getBeginTime()!=null){
            Date beginTime = dailyCreateRequest.getBeginTime();
            Date endTime = Date.from(beginTime.toInstant().plus(Duration.ofDays(1)));
            timeQueryParams = new TimeQueryParams(beginTime,endTime);
        }
        // 執行計算
        int dailyCal = 0;
        int dailyCarbs = 0;
        int dailyProtein = 0;
        int dailyFat = 0;
        PagingQueryParams pagingQueryParams = new PagingQueryParams();
        pagingQueryParams.setLimit(10);
        pagingQueryParams.setOffset(0);
        // 每次menu改動時都去Menu撈資料
        for (Menu menu : menuDao.getMenus(userId,timeQueryParams, pagingQueryParams)) {
            Food food = foodDao.getFoodById(menu.getFoodId());
            // 檢查該食材是否存在
            if (food == null) {
                log.warn("此FoodId: {} 不存在", menu.getFoodId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            int exchange = menu.getExchange();
            // 計算每日攝取熱量&三大營養素
            dailyCal = dailyCal + food.getFoodCal()*exchange;
            dailyCarbs = dailyCarbs + food.getFoodCarbs()*exchange;
            dailyProtein = dailyProtein +food.getFoodProtein()*exchange;
            dailyFat = dailyFat + food.getFoodFat()*exchange;
        }

        // 創建 今日統計表
        DailyParams dailyParams = new DailyParams();
        dailyParams.setDailyCal(dailyCal);
        dailyParams.setDailyCarbs(dailyCarbs);
        dailyParams.setDailyProtein(dailyProtein);
        dailyParams.setDailyFat(dailyFat);
        Integer recommendCal = dailyCreateRequest.getRecommendCal();
        if (recommendCal == null) { recommendCal =Math.round(profile.getBmr()*0.01f)*100;}
        dailyParams.setRecommendCal(recommendCal);
        dailyParams.setAchievePercent(Math.round(dailyCal*100/recommendCal)+"%");

        // 執行記錄創建或更新
        int dailyId = 0;
        List<Daily> todayRecord = dailyDao.getDailyRecords(userId, timeQueryParams, pagingQueryParams);
        if (todayRecord.size() == 1) {
            dailyId = todayRecord.get(0).getDailyId();
            dailyDao.updateDailyRecordForToday(dailyId, dailyParams);
        } else if (todayRecord.isEmpty()) {
            dailyId = dailyDao.createDailyRecordForToday(userId,timeQueryParams.getBeginTime(), dailyParams);
        }
        return dailyId;
    }

    @Override
    public Daily getDailyById(Integer dailyId) {
        Daily daily = dailyDao.getDailyById(dailyId);
        return daily;
    }

    @Override
    public List<Daily> getDailyRecords(Integer userId, TimeQueryParams timeQueryParams, PagingQueryParams pagingQueryParams) {
        return dailyDao.getDailyRecords(userId,timeQueryParams, pagingQueryParams);
    }

    @Override
    public Integer countDailyRecords(Integer userId, TimeQueryParams timeQueryParams) {
        return dailyDao.countDailyRecords(userId,timeQueryParams);
    }

}
