package com.serena.nutritioncalculator.constant;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum FoodLocation {
    @JsonProperty("超商")
    CVS,
    @JsonProperty("麥當勞")
    MC_DONALD,
    @JsonProperty("摩斯漢堡")
    MOS,
    @JsonProperty("星巴克")
    STARBUCKS,
    @JsonProperty("早餐店")
    BREAKFAST,
    @JsonProperty("麥味登")
    MY_WARM_DAY,
    @JsonProperty("好事多 COSTCO")
    COSTCO,
    @JsonProperty("小吃店")
    DINER,
    @JsonProperty("HOT_POT")
    HOT_POT,
    @JsonProperty("LOUISA")
    LOUISA,
    @JsonProperty("便當店")
    BENTO,
    @JsonProperty("八方雲集")
    BAFANG,
    @JsonProperty("自備")
    HOME_COOKING,
    @JsonProperty("其他")
    OTHER
}
