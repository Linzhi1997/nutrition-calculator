package com.serena.nutritioncalculator.controller;


import com.serena.nutritioncalculator.dto.*;
import com.serena.nutritioncalculator.model.Profile;
import com.serena.nutritioncalculator.server.ProfileServer;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProfileController {
    @Autowired
    ProfileServer profileServer;

    // 創造基本資料表
    @PostMapping("/users/{userId}/profiles")
    public ResponseEntity<Profile> createProfile(@PathVariable Integer userId,
                                                @RequestBody @Valid ProfileCreateRequest profileCreateRequest){
        Integer profileId = profileServer.createProfile(userId, profileCreateRequest);
        Profile profile = profileServer.getProfileById(profileId);
        return ResponseEntity.status(HttpStatus.CREATED).body(profile);
    }

    // 獲取歷史基本資料紀錄表
    @GetMapping("/users/{userId}/profilelists")
    public ResponseEntity<List<Profile>> getProfileListByUserId(@PathVariable Integer userId){
        List<Profile> profileList = profileServer.getProfileListByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(profileList);
    }

}
