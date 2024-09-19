package com.serena.nutritioncalculator.controller;

import com.serena.nutritioncalculator.constant.ProfileHistoryType;
import com.serena.nutritioncalculator.dto.ProfileHistoryEntry;
import com.serena.nutritioncalculator.server.ProfileHistoryServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProfileHistoryController {

    @Autowired
    ProfileHistoryServer profileHistoryServer;

    @GetMapping("/users/{userId}/profile-history")
    public ResponseEntity<List<ProfileHistoryEntry>> getHistory(@PathVariable Integer userId,
                                                                @RequestParam ProfileHistoryType profileHistoryType) {
        List<ProfileHistoryEntry> history = profileHistoryServer.getHistory(userId, profileHistoryType);
        return ResponseEntity.status(HttpStatus.OK).body(history);
    }
}
