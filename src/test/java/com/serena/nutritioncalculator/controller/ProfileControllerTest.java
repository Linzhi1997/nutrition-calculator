package com.serena.nutritioncalculator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.serena.nutritioncalculator.dto.ProfileCreateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @Transactional
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
    @Transactional
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
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void get_profile_success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users/2/profilelists");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].userId",equalTo(2)))
                .andExpect(jsonPath("$.[0].age",notNullValue()))
                .andExpect(jsonPath("$.[0].height",notNullValue()))
                .andExpect(jsonPath("$.[0].weight",equalTo(88.0)))
                .andExpect(jsonPath("$.[0].bmi",equalTo(27.8)))
                .andExpect(jsonPath("$.[0].activity",equalTo(1.2)))
                .andExpect(jsonPath("$.[0].bmr",notNullValue()))
                .andExpect(jsonPath("$.[0].goalWeight",notNullValue()))
                .andExpect(jsonPath("$.[0].expectedWeightChange",notNullValue()))
                .andExpect(jsonPath("$.[0].profileCreatedDate",notNullValue()))

                .andExpect(jsonPath("$.[1].userId",equalTo(2)))
                .andExpect(jsonPath("$.[1].age",notNullValue()))
                .andExpect(jsonPath("$.[1].height",notNullValue()))
                .andExpect(jsonPath("$.[1].weight",equalTo(90.0)))
                .andExpect(jsonPath("$.[1].bmi",equalTo(28.4)))
                .andExpect(jsonPath("$.[1].activity",equalTo(1.2)))
                .andExpect(jsonPath("$.[1].bmr",notNullValue()))
                .andExpect(jsonPath("$.[1].goalWeight",notNullValue()))
                .andExpect(jsonPath("$.[1].expectedWeightChange",notNullValue()))
                .andExpect(jsonPath("$.[1].profileCreatedDate",notNullValue()));
    }

    @Test
    @Transactional
    public void get_userNotCreateProfile() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users/1/profilelists");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void delete_profile_success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/users/profiles/1");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent());
    }

    @Test
    @Transactional
    public void delete_notExistProfile() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/users/profiles/100");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }

}