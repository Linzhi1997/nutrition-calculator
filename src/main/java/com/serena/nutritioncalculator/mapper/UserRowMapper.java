package com.serena.nutritioncalculator.mapper;

import com.serena.nutritioncalculator.constant.SexCategory;
import com.serena.nutritioncalculator.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setUserName(rs.getString("user_name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setSex(SexCategory.valueOf(rs.getString("sex")));
        user.setBirth(rs.getDate("birth").toLocalDate());
        user.setUserCreatedDate(rs.getDate("user_created_date"));
        return user;
    }
}
