package com.serena.nutritioncalculator.constant;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum SexCategory {
    @JsonProperty("女性")
    F,
    @JsonProperty("男性")
    M
}
