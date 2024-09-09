package com.serena.nutritioncalculator.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileCreateRequest {

    private Float       height;
    @NotNull
    private Float       weight;
    private Float       activity;
    private Float       goalWeight;
}
