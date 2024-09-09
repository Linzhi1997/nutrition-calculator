package com.serena.nutritioncalculator.controller;

import com.serena.nutritioncalculator.dto.ProfileHistoryEntry;
import com.serena.nutritioncalculator.server.ProfileHistoryServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProfileHistoryController {

    @Autowired
    ProfileHistoryServer profileHistoryServer;

    @GetMapping("/users/{userId}/tdees")
    public ResponseEntity<List<ProfileHistoryEntry>> getTDEEHistory(@PathVariable Integer userId) {
        List<ProfileHistoryEntry> tdeeHistory = profileHistoryServer.getTDEEHistory(userId);
        return ResponseEntity.ok(tdeeHistory);
    }

    @GetMapping("/users/{userId}/bmrs")
    public ResponseEntity<List<ProfileHistoryEntry>> getBMRHistory(@PathVariable Integer userId) {
        List<ProfileHistoryEntry> bmrHistory = profileHistoryServer.getBMRHistory(userId);
        return ResponseEntity.ok(bmrHistory);
    }

    @GetMapping("/users/{userId}/bmis")
    public ResponseEntity<List<ProfileHistoryEntry>> getBMIHistory(@PathVariable Integer userId) {
        List<ProfileHistoryEntry> bmiHistory = profileHistoryServer.getBMIHistory(userId);
        return ResponseEntity.ok(bmiHistory);
    }

    @GetMapping("/users/{userId}/weights")
    public ResponseEntity<List<ProfileHistoryEntry>> getWeightHistory(@PathVariable Integer userId) {
        List<ProfileHistoryEntry> weightHistory = profileHistoryServer.getWeightHistory(userId);
        return ResponseEntity.ok(weightHistory);
    }
}
