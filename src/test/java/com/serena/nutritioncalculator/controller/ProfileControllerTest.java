package com.serena.nutritioncalculator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.serena.nutritioncalculator.dao.ProfileDao;
import com.serena.nutritioncalculator.dto.ProfileCreateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProfileControllerTest {
    @Autowired
    ProfileDao profileDao;
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void create_success() throws Exception{
        // 第一次創建
        ProfileCreateRequest profileCreateRequest = new ProfileCreateRequest();
        profileCreateRequest.setWeight(60.0f);
        profileCreateRequest.setHeight(160f);
        profileCreateRequest.setActivity(1.2f);
        profileCreateRequest.setGoalWeight(52.0f);

        String json = objectMapper.writeValueAsString(profileCreateRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/1/profiles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId", equalTo(1)))
                .andExpect(jsonPath("$.age",notNullValue()))
                .andExpect(jsonPath("$.height",equalTo(160.0)))
                .andExpect(jsonPath("$.weight",equalTo(60.0)))
                .andExpect(jsonPath("$.bmi",notNullValue()))
                .andExpect(jsonPath("$.activity",equalTo(1.2)))
                .andExpect(jsonPath("$.bmr",notNullValue()))
                .andExpect(jsonPath("$.tdee",notNullValue()))
                .andExpect(jsonPath("$.goalWeight",equalTo(52.0)));
    }

    @Test
    public void create_second_time_success() throws Exception{
        // 第二次創建 只有體重必要
        ProfileCreateRequest profileCreateRequest = new ProfileCreateRequest();
        profileCreateRequest.setWeight(78.0f);

        String json = objectMapper.writeValueAsString(profileCreateRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/2/profiles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId", equalTo(2)))
                .andExpect(jsonPath("$.age",notNullValue()))
                .andExpect(jsonPath("$.height",equalTo(178.0)))
                .andExpect(jsonPath("$.weight",equalTo(78.0)))
                .andExpect(jsonPath("$.bmi",notNullValue()))
                .andExpect(jsonPath("$.activity",equalTo(1.2)))
                .andExpect(jsonPath("$.bmr",notNullValue()))
                .andExpect(jsonPath("$.tdee",notNullValue()))
                .andExpect(jsonPath("$.goalWeight",equalTo(72.0)));
    }

    @Test
    public void create_invalidUser() throws Exception {
        // 使用不存在的user創建
        ProfileCreateRequest profileCreateRequest = new ProfileCreateRequest();
        profileCreateRequest.setWeight(80.44f);
        profileCreateRequest.setHeight(175f);
        profileCreateRequest.setActivity(1.2f);
        profileCreateRequest.setGoalWeight(70f);

        String json = objectMapper.writeValueAsString(profileCreateRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/100/profiles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

}