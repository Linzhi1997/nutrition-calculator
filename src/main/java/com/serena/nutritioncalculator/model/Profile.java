package com.serena.nutritioncalculator.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class Profile {

    private Integer     profileId;
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
