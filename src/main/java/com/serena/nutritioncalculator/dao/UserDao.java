package com.serena.nutritioncalculator.dao;


import com.serena.nutritioncalculator.dto.UserRegisterRequest;
import com.serena.nutritioncalculator.model.User;

public interface UserDao {
    Integer createUser(UserRegisterRequest userRegisterRequest);
    User getUserById(Integer userId);
    User getUserByEmail(String email);
}
