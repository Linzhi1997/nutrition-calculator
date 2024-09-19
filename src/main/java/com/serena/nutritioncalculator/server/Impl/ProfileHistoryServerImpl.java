package com.serena.nutritioncalculator.server.Impl;

import com.serena.nutritioncalculator.constant.ProfileHistoryType;
import com.serena.nutritioncalculator.dao.ProfileHistoryDao;
import com.serena.nutritioncalculator.dao.UserDao;
import com.serena.nutritioncalculator.dto.ProfileHistoryEntry;
import com.serena.nutritioncalculator.server.ProfileHistoryServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
public class ProfileHistoryServerImpl implements ProfileHistoryServer {
    private static final Logger log = LoggerFactory.getLogger(ProfileHistoryServerImpl.class);
    @Autowired
    private ProfileHistoryDao profileHistoryDao;
    @Autowired
    private UserDao userDao;

    public List<ProfileHistoryEntry> getHistory(Integer userId,ProfileHistoryType profileHistoryType) {
        if (userDao.getUserById(userId)==null){
            log.warn("user: {}不存在",userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return profileHistoryDao.getProfileHistory(userId, profileHistoryType);
    }
}
