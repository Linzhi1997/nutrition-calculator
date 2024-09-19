package com.serena.nutritioncalculator.server;

import com.serena.nutritioncalculator.constant.ProfileHistoryType;
import com.serena.nutritioncalculator.dto.ProfileHistoryEntry;

import java.util.List;

public interface ProfileHistoryServer {
    List<ProfileHistoryEntry> getHistory(Integer userId, ProfileHistoryType profileHistoryType) ;
}
