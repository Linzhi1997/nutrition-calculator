package com.serena.nutritioncalculator.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProfileHistoryEntry {
    private  Float      value;
    private  LocalDate  profileCreatedDate;
}
