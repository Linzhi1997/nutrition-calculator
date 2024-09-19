package com.serena.nutritioncalculator.controller;

import com.serena.nutritioncalculator.dao.ProfileHistoryDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class ProfileHistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ProfileHistoryDao profileHistoryDao;

    @Test
    public void getTDEE_success() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users/{userId}/profile-history",2)
                .param("profileHistoryType","TDEE");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].value",equalTo(2423.0)))
                .andExpect(jsonPath("$.[0].profileCreatedDate",equalTo("2024-09-10")))
                .andExpect(jsonPath("$.[1].value",equalTo(2390.0)))
                .andExpect(jsonPath("$.[1].profileCreatedDate",equalTo("2024-09-17")));
    }

    @Test
    public void getBMR_success() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users/{userId}/profile-history",2)
                .param("profileHistoryType","BMR");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].value",equalTo(2019.0)))
                .andExpect(jsonPath("$.[0].profileCreatedDate",equalTo("2024-09-10")))
                .andExpect(jsonPath("$.[1].value",equalTo(1992.0)))
                .andExpect(jsonPath("$.[1].profileCreatedDate",equalTo("2024-09-17")));
    }

    @Test
    public void getBMI_success() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users/{userId}/profile-history",2)
                .param("profileHistoryType","BMI");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].value",equalTo(28.4)))
                .andExpect(jsonPath("$.[0].profileCreatedDate",equalTo("2024-09-10")))
                .andExpect(jsonPath("$.[1].value",equalTo(27.8)))
                .andExpect(jsonPath("$.[1].profileCreatedDate",equalTo("2024-09-17")));
    }

    @Test
    public void getWeight_success() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users/{userId}/profile-history",2)
                .param("profileHistoryType","WEIGHT");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].value",equalTo(90.0)))
                .andExpect(jsonPath("$.[0].profileCreatedDate",equalTo("2024-09-10")))
                .andExpect(jsonPath("$.[1].value",equalTo(88.0)))
                .andExpect(jsonPath("$.[1].profileCreatedDate",equalTo("2024-09-17")));

    }

    @Test
    public void getWeight_notExistUser() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users/{userId}/profile-history",100)
                .param("profileHistoryType","WEIGHT");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());

    }
}