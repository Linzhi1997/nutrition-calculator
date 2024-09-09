package com.serena.nutritioncalculator.dao.Impl;

import com.serena.nutritioncalculator.dao.ProfileDao;
import com.serena.nutritioncalculator.dao.UserDao;
import com.serena.nutritioncalculator.dto.ProfileCreateParams;
import com.serena.nutritioncalculator.mapper.ProfileRowMapper;
import com.serena.nutritioncalculator.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProfileDaoImpl implements ProfileDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createProfile(ProfileCreateParams profileCreateParams) {
        String sql = "INSERT INTO profile (user_id,age,height,weight,bmi,activity,bmr,tdee,goal_weight,expected_weight_change,profile_created_date) " +
                " VALUES (:userId,:age,:height,:weight,:bmi,:activity,:bmr,:tdee,:goalWeight,:expectedWeightChange,:profileCreatedDate) ";
        Map<String,Object> map = new HashMap<>();

        map.put("userId", profileCreateParams.getUserId());
        map.put("age", profileCreateParams.getAge());
        map.put("height", profileCreateParams.getHeight());
        map.put("weight", profileCreateParams.getWeight());
        map.put("bmi", profileCreateParams.getBmi());
        map.put("activity", profileCreateParams.getActivity());
        map.put("bmr", profileCreateParams.getBmr());
        map.put("tdee", profileCreateParams.getTdee());
        map.put("goalWeight", profileCreateParams.getGoalWeight());
        map.put("expectedWeightChange", profileCreateParams.getExpectedWeightChange());
        map.put("profileCreatedDate", LocalDate.now());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);
        int profileId = keyHolder.getKey().intValue();

        return profileId;
    }

    @Override
    public Profile getProfileById(Integer profileId) {
        String sql = "SELECT profile_id,user_id,age,height,weight,bmi,activity,bmr,tdee,goal_weight,expected_weight_change,profile_created_date " +
                " FROM profile WHERE profile_id=:profileId";
        Map<String,Object> map = new HashMap<>();
        map.put("profileId",profileId);
        List<Profile> profileList = namedParameterJdbcTemplate.query(sql,map,new ProfileRowMapper());
        if(profileList.size()>0){
            return profileList.get(0);
        }else {
            return null;
        }
    }


    @Override
    public Profile getLastProfileByUserId(Integer userId) {
        List<Profile> profileList = getProfileListByUserId(userId);

        if(profileList.size()>0){
            return profileList.get(0);
        }else {
            return null;
        }
    }

    @Override
    public List<Profile> getProfileListByUserId(Integer userId) {
        String sql = "SELECT profile_id,user_id,age,height,weight,bmi,activity,bmr,tdee,goal_weight,expected_weight_change,profile_created_date " +
                " FROM profile WHERE user_id=:userId Order by profile_id DESC ";
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        List<Profile> profileList = namedParameterJdbcTemplate.query(sql,map,new ProfileRowMapper());

        return profileList;
    }


}
