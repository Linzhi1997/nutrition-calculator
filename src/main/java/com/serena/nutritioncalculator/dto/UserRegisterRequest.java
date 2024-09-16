package com.serena.nutritioncalculator.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.serena.nutritioncalculator.constant.SexCategory;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserRegisterRequest {
    @NotEmpty
    private String      userName;
    @NotEmpty
    @Email
    private String      email;
    @NotEmpty
    private String      password;
    @NotNull
    private SexCategory sex;
    @NotNull
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate   birth;
}
