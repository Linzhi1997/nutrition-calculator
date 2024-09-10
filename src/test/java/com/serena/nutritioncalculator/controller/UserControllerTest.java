package com.serena.nutritioncalculator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.serena.nutritioncalculator.constant.SexCategory;
import com.serena.nutritioncalculator.dao.*;
import com.serena.nutritioncalculator.dto.*;
import com.serena.nutritioncalculator.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTest {
    @Autowired
    private UserDao userDao;
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void register_success() throws Exception{
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setUserName("One");
        userRegisterRequest.setEmail("test1@gmail.com");
        userRegisterRequest.setPassword("password123");
        userRegisterRequest.setBirth(LocalDate.parse("1990-01-01"));
        userRegisterRequest.setSex(SexCategory.valueOf("M"));

        String json = objectMapper.writeValueAsString(userRegisterRequest);

        // 創建Http request
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        // 執行Http request
        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userName", equalTo("One")))
                .andExpect(jsonPath("$.userId", notNullValue()))
                .andExpect(jsonPath("$.email",equalTo("test1@gmail.com")))
                .andExpect(jsonPath("$.birth",notNullValue()))
                .andExpect(jsonPath("$.sex",notNullValue()))
                .andExpect(jsonPath("$.userCreatedDate",notNullValue()));
        // 檢查密碼不為明碼
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());
        assertNotEquals(userRegisterRequest.getPassword(),user.getPassword());

    }

    @Test
    public void register_invalidEmailFormat() throws Exception {
        // 使用格式錯誤的email註冊
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setUserName("One");
        userRegisterRequest.setEmail("3gd8e7q34l9");
        userRegisterRequest.setPassword("123");
        userRegisterRequest.setBirth(LocalDate.parse("1990-01-01"));
        userRegisterRequest.setSex(SexCategory.valueOf("M"));

        String json = objectMapper.writeValueAsString(userRegisterRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    @Test
    public void register_emailAlreadyExist() throws Exception {
        // 註冊一個帳號
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setUserName("Two");
        userRegisterRequest.setEmail("test2@gmail.com");
        userRegisterRequest.setPassword("123");
        userRegisterRequest.setBirth(LocalDate.parse("1990-01-01"));
        userRegisterRequest.setSex(SexCategory.valueOf("M"));

        String json = objectMapper.writeValueAsString(userRegisterRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(201));

        // 再次使用同個 email 註冊
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    // 登入
    @Test
    public void login_success() throws Exception {
        // 註冊新帳號
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setUserName("Three");
        userRegisterRequest.setEmail("test3@gmail.com");
        userRegisterRequest.setPassword("123");
        userRegisterRequest.setBirth(LocalDate.parse("1990-01-01"));
        userRegisterRequest.setSex(SexCategory.valueOf("M"));

        register(userRegisterRequest);

        // 再測試登入功能
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setUserName(userRegisterRequest.getUserName());
        userLoginRequest.setEmail(userRegisterRequest.getEmail());
        userLoginRequest.setPassword(userRegisterRequest.getPassword());

        String json = objectMapper.writeValueAsString(userRegisterRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.userId", notNullValue()))
                .andExpect(jsonPath("$.userName",equalTo("Three")))
                .andExpect(jsonPath("$.email", equalTo("test3@gmail.com"))) // json格式中的email為e-mail
                .andExpect(jsonPath("$.birth", notNullValue()))
                .andExpect(jsonPath("$.sex", notNullValue()))
                .andExpect(jsonPath("$.userCreatedDate",notNullValue()));
    }

    @Test
    public void login_wrongPassword() throws Exception {
        // 註冊新帳號
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setUserName("Four");
        userRegisterRequest.setEmail("test4@gmail.com");
        userRegisterRequest.setPassword("123");
        userRegisterRequest.setBirth(LocalDate.parse("1990-01-01"));
        userRegisterRequest.setSex(SexCategory.valueOf("M"));

        register(userRegisterRequest);

        // 測試密碼輸入錯誤的情況
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail(userRegisterRequest.getEmail());
        userLoginRequest.setPassword("unknown");

        String json = objectMapper.writeValueAsString(userLoginRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    @Test
    public void login_invalidEmailFormat() throws Exception {
        // 使用格式錯誤的email登入
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setUserName("Four");
        userLoginRequest.setEmail("abcde12324");
        userLoginRequest.setPassword("123");

        String json = objectMapper.writeValueAsString(userLoginRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    @Test
    public void login_emailNotExist() throws Exception {
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setUserName("Four");
        userLoginRequest.setEmail("unknown@gmail.com");
        userLoginRequest.setPassword("123");

        String json = objectMapper.writeValueAsString(userLoginRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }
    // 提煉register
    private void register(UserRegisterRequest userRegisterRequest) throws Exception {
        String json = objectMapper.writeValueAsString(userRegisterRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(201));
    }
}