package com.serena.nutritioncalculator.mapper;

import com.serena.nutritioncalculator.constant.ProfileHistoryType;
import com.serena.nutritioncalculator.dto.ProfileHistoryEntry;
import org.springframework.jdbc.core.RowMapper;

import java.sql.*;


public class ProfileHistoryRowMapper implements RowMapper<ProfileHistoryEntry> {

    private final ProfileHistoryType type;

    public ProfileHistoryRowMapper(ProfileHistoryType type) {
        this.type = type;
    }

    @Override
    public ProfileHistoryEntry mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProfileHistoryEntry profileHistoryEntry = new ProfileHistoryEntry();
        profileHistoryEntry.setProfileCreatedDate(rs.getDate("profile_created_date").toLocalDate());
        profileHistoryEntry.setValue(rs.getFloat(type.name().toLowerCase()));
        return profileHistoryEntry;
    }
}
