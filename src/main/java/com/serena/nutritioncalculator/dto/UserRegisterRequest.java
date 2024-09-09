package com.serena.nutritioncalculator.dto;

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
    private String  userName;
    @NotEmpty
    @Email
    private String  email;
    @NotEmpty
    private String  password;
    @NotNull
    private SexCategory sex;
    @NotNull
    private LocalDate   birth;
}
