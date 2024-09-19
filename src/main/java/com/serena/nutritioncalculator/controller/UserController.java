package com.serena.nutritioncalculator.controller;


import com.serena.nutritioncalculator.dto.*;
import com.serena.nutritioncalculator.model.*;
import com.serena.nutritioncalculator.server.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    UserServer userServer;
    @Autowired
    DailyServer dailyServer;
    @Autowired
    ProfileServer profileServer;

    @PostMapping("/users/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest){
        Integer userId = userServer.register(userRegisterRequest);
        User user = userServer.getUserById(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/users/login")
    public ResponseEntity<User> login(@RequestBody @Valid UserLoginRequest userLoginRequest){
        User user = userServer.login(userLoginRequest);
        // 在登錄時 創建今日統計表
        dailyServer.updateDailyRecordForToday(user.getUserId(), null);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/users/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable @Email String email) {
        User user = userServer.getUserByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

}
