package com.serena.nutritioncalculator.controller;


import com.serena.nutritioncalculator.dto.*;
import com.serena.nutritioncalculator.model.*;
import com.serena.nutritioncalculator.server.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    UserServer userServer;

    @PostMapping("/users/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest){
        Integer userId = userServer.register(userRegisterRequest);
        User user = userServer.getUserById(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/users/login")
    public ResponseEntity<User> login(@RequestBody @Valid UserLoginRequest userLoginRequest){
        User user = userServer.login(userLoginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/users/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userServer.getUserByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

}
