package com.serena.nutritioncalculator.server;

import com.serena.nutritioncalculator.dto.UserLoginRequest;
import com.serena.nutritioncalculator.dto.UserRegisterRequest;
import com.serena.nutritioncalculator.model.User;

public interface UserServer {
    Integer register(UserRegisterRequest userRegisterRequest);
    User login(UserLoginRequest userLoginRequest);
    User getUserById(Integer userId);
    User getUserByEmail(String email);
}
