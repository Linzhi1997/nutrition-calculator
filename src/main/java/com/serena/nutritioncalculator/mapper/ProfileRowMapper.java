package com.serena.nutritioncalculator.mapper;

import com.serena.nutritioncalculator.model.Profile;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileRowMapper implements RowMapper<Profile> {

    @Override
    public Profile mapRow(ResultSet rs, int rowNum) throws SQLException {
        Profile profile = new Profile();
        profile.setProfileId(rs.getInt("profile_id"));
        profile.setUserId(rs.getInt("user_id"));
        profile.setAge(rs.getInt("age"));
        profile.setHeight(rs.getFloat("height"));
        profile.setWeight(rs.getFloat("weight"));
        profile.setBmi(rs.getFloat("BMI"));
        profile.setActivity(rs.getFloat("activity"));
        profile.setBmr(rs.getInt("BMR"));
        profile.setTdee(rs.getInt("TDEE"));
        profile.setGoalWeight(rs.getFloat("goal_weight"));
        profile.setExpectedWeightChange(rs.getString("expected_weight_change"));
        profile.setProfileCreatedDate(rs.getDate("profile_created_date").toLocalDate());
        return profile;
    }
}
