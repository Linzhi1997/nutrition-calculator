package com.serena.nutritioncalculator.server;

import com.serena.nutritioncalculator.dto.ProfileHistoryEntry;

import java.util.List;

public interface ProfileHistoryServer {
    List<ProfileHistoryEntry> getTDEEHistory(Integer userId) ;
    List<ProfileHistoryEntry> getBMRHistory(Integer userId) ;
    List<ProfileHistoryEntry> getBMIHistory(Integer userId) ;
    List<ProfileHistoryEntry> getWeightHistory(Integer userId);
}
