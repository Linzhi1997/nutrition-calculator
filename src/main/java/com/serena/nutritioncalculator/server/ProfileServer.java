package com.serena.nutritioncalculator.server;


import com.serena.nutritioncalculator.dto.ProfileCreateRequest;
import com.serena.nutritioncalculator.model.Profile;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


public interface ProfileServer {
    Integer createProfile(Integer userId, ProfileCreateRequest profileCreateRequest);
    Profile getProfileById(Integer profileId);
    Profile getLastProfileByUserId(Integer userId);
    List<Profile> getProfileListByUserId(Integer userId);
    void deleteProfileById(Integer profileId);
}
