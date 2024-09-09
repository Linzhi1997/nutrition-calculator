package com.serena.nutritioncalculator.dao;

import com.serena.nutritioncalculator.dto.ProfileCreateParams;
import com.serena.nutritioncalculator.model.Profile;
import org.springframework.stereotype.Component;


import java.util.List;

public interface ProfileDao {
    Integer createProfile(ProfileCreateParams profileCreateParams);
    Profile getProfileById(Integer profileId);
    Profile getLastProfileByUserId(Integer userId);
    List<Profile> getProfileListByUserId(Integer userId);
}
