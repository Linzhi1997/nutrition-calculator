package com.serena.nutritioncalculator.server.Impl;

import com.serena.nutritioncalculator.constant.SexCategory;
import com.serena.nutritioncalculator.dao.ProfileHistoryDao;
import com.serena.nutritioncalculator.dao.ProfileDao;
import com.serena.nutritioncalculator.dao.UserDao;
import com.serena.nutritioncalculator.dto.ProfileCreateParams;
import com.serena.nutritioncalculator.dto.ProfileCreateRequest;
import com.serena.nutritioncalculator.model.Profile;
import com.serena.nutritioncalculator.model.User;
import com.serena.nutritioncalculator.server.ProfileServer;
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
public class ProfileServerImpl implements ProfileServer {

    private static final Logger log = LoggerFactory.getLogger(ProfileServerImpl.class);
    @Autowired
    ProfileDao profileDao;
    @Autowired
    UserDao userDao;

    @Override
    public Integer createProfile(Integer userId, ProfileCreateRequest profileCreateRequest) {

        User user = userDao.getUserById(userId);
        Profile profile = profileDao.getLastProfileByUserId(userId);
        // 檢查 user 是否存在
        if(user==null){
            log.warn("該user {}不存在", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        ProfileCreateParams profileCreateParams = new ProfileCreateParams();
        profileCreateParams.setUserId(userId);

        int age = calculateAge(user.getBirth());
        profileCreateParams.setAge(age);
        // 自動設定height為上一次
        Float height = profileCreateRequest.getHeight();
        if (height == null && profile.getHeight() == null) {
            log.warn("user {} 尚未輸入身高", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else if (height == null) {
            height = profile.getHeight(); // 若無輸入height 則獲取前一次身高
        }
        // 檢查 weight 是否輸入
        Float weight = profileCreateRequest.getWeight();
        if (weight == null) {
            log.warn("user {} 尚未輸入體重", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        // 自動設定activity為上一次
        Float activity = profileCreateRequest.getActivity();
        if (activity == null && profile.getActivity() == null) {
            log.warn("user {} 尚未輸入活動量", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else if (activity == null) {
            activity = profile.getActivity();
        }

        profileCreateParams.setHeight(height);
        profileCreateParams.setWeight(weight);
        profileCreateParams.setBmi(calculateBMI(weight, height));
        profileCreateParams.setActivity(activity);
        // 根據基本資料 重新計算BMR
        int BMR = calculateBMR(user.getSex(), weight, height, age);
        profileCreateParams.setBmr(BMR);
        // 根據基本資料 重新計算TDEE
        int TDEE = calculateTDEE(BMR, activity);
        profileCreateParams.setTdee(TDEE);
        // 自動設定goalWeight為上一次
        Float goalWeight = profileCreateRequest.getGoalWeight();
        if (goalWeight == null && profile.getGoalWeight() == null) {
            goalWeight = profileCreateRequest.getWeight();
        } else if (goalWeight == null) {
            goalWeight = profile.getGoalWeight();
        }
        profileCreateParams.setGoalWeight(goalWeight);
        profileCreateParams.setExpectedWeightChange(calculateExpectedWeightChange(weight, goalWeight, TDEE, BMR));
        profileCreateParams.setProfileCreatedDate(new Date());

        return profileDao.createProfile(profileCreateParams);
    }

    @Override
    public Profile getProfileById(Integer profileId) {
        Profile profile = profileDao.getProfileById(profileId);
        if (profile == null) {
            log.warn("Profile Id :{} 不存在", profileId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return profile;
    }


    @Override
    public Profile getLastProfileByUserId(Integer userId) {
        Profile profile = profileDao.getLastProfileByUserId(userId);
        if (profile == null) {
            log.warn("User: {} 尚未輸入基本資料", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return profile;
    }

    @Override
    public List<Profile> getProfileListByUserId(Integer userId) {
        List<Profile> profileList = profileDao.getProfileListByUserId(userId);
        if (profileList == null) {
            log.warn("User: {} 尚未建立熱量表", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return profileList;
    }

    @Override
    public void deleteProfileById(Integer profileId) {
        profileDao.deleteProfileById(profileId);
    }

    // 自動計算年齡
    private Integer calculateAge(LocalDate birth) {
        if (birth == null) {
            return null;
        }
        Integer age = Period.between(birth, LocalDate.now()).getYears();
        return age;
    }

    private Float calculateBMI(float weight,float height) {
        if (weight == 0f || height == 0f) {
            return null;
        }
        Float BMI = Math.round(weight * 100000 / (height * height)) * 0.1f;
        return BMI;
    }

    private Integer calculateBMR(SexCategory sex, float weight, float height, int age) {
        Integer BMR=0;
        if (sex == null || weight == 0f || height == 0f || age == 0) {
            return null;
        }
        if (sex == SexCategory.M) {
            BMR = Math.round((13.7f * weight) + (5.0f * height) - (6.8f * age) + 66);
        } else {
            BMR = Math.round((9.6f * weight) + (1.8f * height) - (4.7f * age) + 655);
        }
        return BMR;
    }

    private Integer calculateTDEE(int BMR,float activity) {
        if (BMR == 0 || activity == 0f) {
            return null;
        }
        Integer TDEE = Math.round(BMR * activity);
        return TDEE;
    }

    private String calculateExpectedWeightChange(float weight,float goalWeight,int TDEE,int BMR) {
        if (weight == 0f || goalWeight == 0f || TDEE == 0 || BMR == 0) {
            return "無法計算預期體重變化";
        }

        if (Float.compare(goalWeight, weight) == 0.0) {
            return "維持目前體重";
        } else if (goalWeight < weight) {
            float weeklyLoss = (float) Math.round((TDEE - BMR) / 110.0) * 0.1f;
            return String.format("理想的體重下降速度：%.1f kg/週", weeklyLoss);
        } else {
            float minGain = Math.round(weight * 0.025f) * 0.1f;
            float maxGain = Math.round(weight * 0.05f) * 0.1f;
            return String.format("理想的體重上升速度：%.1f kg/週 ~ %.1f kg/週 之間", minGain, maxGain);
        }
    }

}
