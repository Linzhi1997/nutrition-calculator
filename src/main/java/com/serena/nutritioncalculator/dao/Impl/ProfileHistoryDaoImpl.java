package com.serena.nutritioncalculator.dao.Impl;

import com.serena.nutritioncalculator.constant.ProfileHistoryType;
import com.serena.nutritioncalculator.dao.ProfileHistoryDao;
import com.serena.nutritioncalculator.dto.ProfileHistoryEntry;
import com.serena.nutritioncalculator.mapper.ProfileHistoryRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProfileHistoryDaoImpl implements ProfileHistoryDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<ProfileHistoryEntry>  getProfileHistory(Integer userId, ProfileHistoryType type) {
        String sql = " SELECT profile_created_date," + type.name().toLowerCase() +
                " FROM profile WHERE user_id=:userId Order by profile_created_date ASC ";
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);

        return namedParameterJdbcTemplate.query(sql, map, new ProfileHistoryRowMapper(type));
    }
}
