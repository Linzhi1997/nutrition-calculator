package com.serena.nutritioncalculator.dao;

import com.serena.nutritioncalculator.constant.ProfileHistoryType;
import com.serena.nutritioncalculator.dto.ProfileHistoryEntry;
import org.springframework.stereotype.Component;

import java.util.List;


public interface ProfileHistoryDao {
    List<ProfileHistoryEntry>  getProfileHistory(Integer userId, ProfileHistoryType type);
}
