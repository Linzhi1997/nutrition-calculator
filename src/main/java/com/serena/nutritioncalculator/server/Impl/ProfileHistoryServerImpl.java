package com.serena.nutritioncalculator.server.Impl;

import com.serena.nutritioncalculator.constant.ProfileHistoryType;
import com.serena.nutritioncalculator.dao.ProfileHistoryDao;
import com.serena.nutritioncalculator.dto.ProfileHistoryEntry;
import com.serena.nutritioncalculator.server.ProfileHistoryServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProfileHistoryServerImpl implements ProfileHistoryServer {
    @Autowired
    private ProfileHistoryDao profileHistoryDao;

    public List<ProfileHistoryEntry> getTDEEHistory(Integer userId) {
        return profileHistoryDao.getProfileHistory(userId, ProfileHistoryType.TDEE);
    }

    public List<ProfileHistoryEntry> getBMRHistory(Integer userId) {
        return profileHistoryDao.getProfileHistory(userId, ProfileHistoryType.BMR);
    }

    public List<ProfileHistoryEntry> getBMIHistory(Integer userId) {
        return profileHistoryDao.getProfileHistory(userId, ProfileHistoryType.BMI);
    }

    public List<ProfileHistoryEntry> getWeightHistory(Integer userId) {
        return profileHistoryDao.getProfileHistory(userId, ProfileHistoryType.WEIGHT);
    }
}
