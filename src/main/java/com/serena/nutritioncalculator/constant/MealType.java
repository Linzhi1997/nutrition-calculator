package com.serena.nutritioncalculator.constant;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum MealType {
    @JsonProperty("早餐")
    B,
    @JsonProperty("早點")
    BS,
    @JsonProperty("午餐")
    L,
    @JsonProperty("午點")
    LS,
    @JsonProperty("晚餐")
    D,
    @JsonProperty("晚點")
    DS
}
