package com.serena.nutritioncalculator.server.Impl;

import com.serena.nutritioncalculator.dao.FoodDao;
import com.serena.nutritioncalculator.dao.MenuDao;
import com.serena.nutritioncalculator.dao.ProfileDao;
import com.serena.nutritioncalculator.dao.UserDao;
import com.serena.nutritioncalculator.dto.MenuRequest;
import com.serena.nutritioncalculator.dto.MenuQueryParams;
import com.serena.nutritioncalculator.model.*;
import com.serena.nutritioncalculator.server.MenuServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.*;
import java.util.Date;
import java.util.List;

@Component
public class MenuServerImpl implements MenuServer {
    private static final Logger log = LoggerFactory.getLogger(MenuServerImpl.class);
    @Autowired
    UserDao userDao;
    @Autowired
    ProfileDao profileDao;
    @Autowired
    MenuDao menuDao;
    @Autowired
    FoodDao foodDao;

    @Override
    public Integer createMenu(Integer usersId, MenuRequest menuRequest) {
        User user = userDao.getUserById(usersId);
        if(user==null){
            log.warn("此UserId: {}不存在", usersId);
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Profile profile = profileDao.getLastProfileByUserId(usersId);
        if(profile==null){
            log.warn("此UserId: {} 未創建熱量表", usersId);
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Food food = foodDao.getFoodById(menuRequest.getFoodId());

        if(food==null){
            log.warn("此FoodId: {} 不存在", menuRequest.getFoodId());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if(menuRequest.getExchange()==null){
            menuRequest.setExchange(1);
        }
        return menuDao.createMenu(usersId, menuRequest);
    }

    @Override
    public void deleteMenu(Integer menuId) {
        if(getMenuById(menuId)==null){
            log.warn("MenuId: {} 不存在", menuId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        menuDao.deleteMenu(menuId);
    }

    @Override
    public void updateMenu(Integer menuId, MenuRequest menuRequest) {
        if(menuId==null){
            log.warn("MenuId: {} 不存在", menuId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        menuDao.updateMenu(menuId, menuRequest);
    }

    @Override
    public Menu getMenuById(Integer menuId) {
        if(menuId==null){
            log.warn("MenuId: {} 不存在", menuId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return menuDao.getMenuById(menuId);
    }

    @Override
    public List<MenuFood> getMenuFoods(MenuQueryParams menuQueryParams) {
        menuQueryParams = setTime(menuQueryParams);
        return menuDao.getMenuFoods(menuQueryParams);
    }

    @Override
    public Integer getMenuTotal(MenuQueryParams menuQueryParams) {
        menuQueryParams = setTime(menuQueryParams);
        return menuDao.getMenuTotal(menuQueryParams);
    }

    @Override
    public DailyIntake getDailyIntake(MenuQueryParams menuQueryParams) {
        // 設定時間為自設或預設 且<24h
        menuQueryParams = setTime(menuQueryParams);
        setDailyTimeInADay(menuQueryParams);
        List<MenuFood> menuFoodList = menuDao.getMenuFoods(menuQueryParams);
        // 計算一日攝取總熱量及三大營養素
        int dailyCal = 0;
        int dailyCarbs = 0;
        int dailyProtein = 0;
        int dailyFat = 0;

        for (MenuFood menuFood:menuFoodList){
            dailyCal += menuFood.getExchange() * menuFood.getFoodCal();
            dailyCarbs += menuFood.getExchange() * menuFood.getFoodCarbs();
            dailyProtein += menuFood.getExchange() * menuFood.getFoodProtein();
            dailyFat += menuFood.getExchange() * menuFood.getFoodFat();
        }

        int recommendCal =setRecommendCal(menuQueryParams);
        DailyIntake dailyIntake = new DailyIntake();
        dailyIntake.setUserId(menuQueryParams.getUserId());
        dailyIntake.setRecommendCal(recommendCal);
        dailyIntake.setDailyCal(dailyCal);
        dailyIntake.setDailyCarbs(dailyCarbs);
        dailyIntake.setDailyProtein(dailyProtein);
        dailyIntake.setDailyFat(dailyFat);
        String achievePercent = String.format("%.2f%%", (dailyCal * 100.0 / recommendCal));
        dailyIntake.setAchievePercent(achievePercent);

        return dailyIntake;
    }

    private Integer setRecommendCal(MenuQueryParams menuQueryParams){
        // 建議熱量設為BMR或自設值
        int userId = menuQueryParams.getUserId();
        Integer recommendCal = menuQueryParams.getRecommendCal();
        if (recommendCal == null) {
            recommendCal = Math.round(profileDao.getLastProfileByUserId(userId).getBmr()/100)*100; //
        }
        return recommendCal;
    }

    // 預設查詢時間為當日 或 自主設定
    private MenuQueryParams setTime(MenuQueryParams menuQueryParams){
        Date menuBeginTime = menuQueryParams.getMenuBeginTime();
        Date menuEndTime = menuQueryParams.getMenuEndTime();

        if ((menuBeginTime == null || menuEndTime == null)){
            LocalDate today = LocalDate.now();
            LocalDateTime beginOfDay = today.atTime(LocalTime.MIN); // 當天的 00:00:00
            LocalDateTime endOfDay = today.atTime(LocalTime.MAX);   // 當天的 23:59:59

            // 轉換 LocalDateTime -> Date
            Date beginDate = Date.from(beginOfDay.atZone(ZoneId.systemDefault()).toInstant());
            Date endDate = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());

            menuQueryParams.setMenuBeginTime(beginDate);
            menuQueryParams.setMenuEndTime(endDate);
        } else if (menuBeginTime.after(menuEndTime) == true) {
            log.warn("起始時間晚於結束時間，請重新輸入");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else if (menuBeginTime != null && menuEndTime != null) {
            menuQueryParams.setMenuBeginTime(menuBeginTime);
            menuQueryParams.setMenuEndTime(menuEndTime);
        }

        return menuQueryParams;
    }

    // 設定時間需<24h
    private void setDailyTimeInADay(MenuQueryParams menuQueryParams){
        Duration duration = Duration.between(menuQueryParams.getMenuBeginTime().toInstant(),menuQueryParams.getMenuEndTime().toInstant());
        if(duration.toHours()>=24){
            log.warn("時間區間大於24小時，請重新輸入");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}
