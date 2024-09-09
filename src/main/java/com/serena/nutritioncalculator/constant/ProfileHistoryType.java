package com.serena.nutritioncalculator.constant;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ProfileHistoryType {
    TDEE,
    BMR,
    BMI,
    @JsonProperty("體重")
    WEIGHT
}
