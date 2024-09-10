package com.serena.nutritioncalculator.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ProfileHistoryEntry {
    private  Float      value;
    private  Date       profileCreatedDate;
}
