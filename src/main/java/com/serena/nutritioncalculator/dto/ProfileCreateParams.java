package com.serena.nutritioncalculator.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProfileCreateParams {
    private Integer     userId;
    private Integer     age;
    private Float       height;
    private Float       weight;
    private Float       bmi;
    private Float       activity;
    private Integer     bmr;
    private Integer     tdee;
    private Float       goalWeight;
    private String      expectedWeightChange;
    private LocalDate   profileCreatedDate;
}
