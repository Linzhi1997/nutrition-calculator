package com.serena.nutritioncalculator.dao.Impl;

import com.serena.nutritioncalculator.dao.UserDao;
import com.serena.nutritioncalculator.dto.UserRegisterRequest;
import com.serena.nutritioncalculator.mapper.UserRowMapper;
import com.serena.nutritioncalculator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserDaoImpl implements UserDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createUser(UserRegisterRequest userRegisterRequest) {
        String sql = "INSERT INTO users (user_name,email,password,sex,birth,user_created_date) " +
                " VALUES (:userName,:email,:password,:sex,:birth,:userCreatedDate)";
        Map<String,Object> map = new HashMap<>();

        map.put("userName",userRegisterRequest.getUserName());
        map.put("email",userRegisterRequest.getEmail());
        map.put("password",userRegisterRequest.getPassword());
        map.put("sex",userRegisterRequest.getSex().toString());
        map.put("birth",userRegisterRequest.getBirth());
        map.put("userCreatedDate",new Date());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);
        Integer userId = keyHolder.getKey().intValue();
        return userId;
    }



    @Override
    public User getUserById(Integer userId) {
        String sql = "SELECT user_id,user_name,email,password,sex,birth,user_created_date FROM users " +
                "WHERE user_id = :userId";
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        List<User> users = namedParameterJdbcTemplate.query(sql,map,new UserRowMapper());
        if(users.size()>0){
            return users.get(0);
        }else{
            return null;
        }
    }

    @Override
    public User getUserByEmail(String email) {
        String sql = "SELECT user_id,user_name,email,password,sex,birth,user_created_date FROM users " +
                "WHERE email = :email";
        Map<String,Object> map = new HashMap<>();
        map.put("email",email);
        List<User> userList = namedParameterJdbcTemplate.query(sql,map,new UserRowMapper());
        if(userList.size()>0){
            return userList.get(0);
        }else{
            return null;
        }
    }

}
