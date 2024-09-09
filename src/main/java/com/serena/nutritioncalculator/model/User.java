package com.serena.nutritioncalculator.model;


import com.serena.nutritioncalculator.constant.SexCategory;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
@Getter
@Setter
public class User {
    private Integer userId;
    private String  userName;
    private String  email;
    private String  password;
    private SexCategory sex;
    private LocalDate   birth;
    private Date    userCreatedDate;
}
