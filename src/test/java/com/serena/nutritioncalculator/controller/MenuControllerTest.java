package com.serena.nutritioncalculator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.serena.nutritioncalculator.constant.MealType;
import com.serena.nutritioncalculator.dto.MenuItem;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
public class MenuControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    @Test
    public void create_success() throws Exception{
        MenuItem menuItem = new MenuItem();
        menuItem.setMealType(MealType.B);
        menuItem.setFoodId(1);
        menuItem.setExchange(2);

        String json = objectMapper.writeValueAsString(menuItem);

        // 創建Http request
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/{userId}/menus",2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        // 執行Http request
        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.menuId",notNullValue()))
                .andExpect(jsonPath("$.userId", equalTo(2)))
                .andExpect(jsonPath("$.mealType",equalTo("早餐")))
                .andExpect(jsonPath("$.foodId",equalTo(1)))
                .andExpect(jsonPath("$.exchange",equalTo(2)))
                .andExpect(jsonPath("$.lastModifiedDate",notNullValue()))
                .andExpect(jsonPath("$.foodName",notNullValue()))
                .andExpect(jsonPath("$.foodCal",notNullValue()))
                .andExpect(jsonPath("$.foodCarbs",notNullValue()))
                .andExpect(jsonPath("$.foodProtein",notNullValue()))
                .andExpect(jsonPath("$.foodFat",notNullValue()))
                .andExpect(jsonPath("$.foodLocation",notNullValue()));

    }

    @Test
    public void profile_not_found() throws Exception{
        MenuItem menuItem = new MenuItem();
        menuItem.setMealType(MealType.B);
        menuItem.setFoodId(1);
        menuItem.setExchange(2);

        String json = objectMapper.writeValueAsString(menuItem);

        // 創建Http request
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/{userId}/menus",1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        // 執行Http request
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void get_menu_success() throws Exception{
        // 創建Http request
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users/{userId}/menus",2)
                .param("beginDate", "2024-09-11 08:00:00")
                .param("endDate", "2024-09-12 08:00:00")
                .param("recommendCal", "1700")
                .param("limit", "10")
                .param("offset", "0");

        // 執行Http request
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultsList[0].menuId",notNullValue()))
                .andExpect(jsonPath("$.resultsList[0].userId", equalTo(2)))
                .andExpect(jsonPath("$.resultsList[0].mealType",notNullValue()))
                .andExpect(jsonPath("$.resultsList[0].foodId",notNullValue()))
                .andExpect(jsonPath("$.resultsList[0].exchange",notNullValue()))
                .andExpect(jsonPath("$.resultsList[0].lastModifiedDate",notNullValue()));
    }

    @Test
    public void get_invalidTimeFormat() throws Exception{

        String beginTime = "2024-09-11@08:00:00";
        String endTime = "2024-09-12@08:00:00";
        Integer recommendCal = 1700;

        // 創建Http request
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users/{userId}/menus",2)
                .param("beginDate", beginTime)
                .param("endDate", endTime)
                .param("recommendCal", recommendCal.toString())
                .param("limit", "10")
                .param("offset", "0");

        // 執行Http request
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void get_invalidTimeDuration() throws Exception{

        String beginTime = "3000-09-11 08:00:00";
        String endTime = "3000-09-12 08:00:00";
        Integer recommendCal = 1700;

        // 創建Http request
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users/{userId}/menus",2)
                .param("beginDate", beginTime)
                .param("endDate", endTime)
                .param("recommendCal", recommendCal.toString())
                .param("limit", "10")
                .param("offset", "0");

        // 執行Http request
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Transactional
    @Test
    public void delete_menu_success() throws Exception{
        // 創建Http request
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/menus/{menuId}",1);

        // 執行Http request
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Transactional
    @Test
    public void update_menu_success() throws Exception{
        MenuItem menuItem = new MenuItem();
        menuItem.setFoodId(7);
        menuItem.setMealType(MealType.B);
        menuItem.setExchange(1);

        String json = objectMapper.writeValueAsString(menuItem);
        // 創建Http request
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/menus/{menuId}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // 執行Http request
        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.menuId",equalTo(1)))
                .andExpect(jsonPath("$.userId",equalTo(2)))
                .andExpect(jsonPath("$.exchange",equalTo(1)))
                .andExpect(jsonPath("$.mealType",equalTo("早餐")))
                .andExpect(jsonPath("$.lastModifiedDate",notNullValue()))
                .andExpect(jsonPath("$.foodId",equalTo(7)))
                .andExpect(jsonPath("$.foodName",notNullValue()))
                .andExpect(jsonPath("$.foodCal",notNullValue()))
                .andExpect(jsonPath("$.foodCarbs",notNullValue()))
                .andExpect(jsonPath("$.foodProtein",notNullValue()))
                .andExpect(jsonPath("$.foodFat",notNullValue()))
                .andExpect(jsonPath("$.foodLocation",notNullValue()));
    }

    @Transactional
    @Test
    public void update_success_oneArguments() throws Exception{
        MenuItem menuItem = new MenuItem();
        menuItem.setFoodId(11);

        String json = objectMapper.writeValueAsString(menuItem);
        // 創建Http request
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/menus/{menuId}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // 執行Http request
        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.menuId",equalTo(1)))
                .andExpect(jsonPath("$.userId",equalTo(2)))
                .andExpect(jsonPath("$.exchange",equalTo(1)))
                .andExpect(jsonPath("$.mealType",equalTo("早餐")))
                .andExpect(jsonPath("$.lastModifiedDate",notNullValue()))
                .andExpect(jsonPath("$.foodId",equalTo(11)))
                .andExpect(jsonPath("$.foodName",notNullValue()))
                .andExpect(jsonPath("$.foodCal",notNullValue()))
                .andExpect(jsonPath("$.foodCarbs",notNullValue()))
                .andExpect(jsonPath("$.foodProtein",notNullValue()))
                .andExpect(jsonPath("$.foodFat",notNullValue()))
                .andExpect(jsonPath("$.foodLocation",notNullValue()));
    }

    @Test
    public void update_foodNotExist() throws Exception{
        MenuItem menuItem = new MenuItem();
        menuItem.setFoodId(1000);

        String json = objectMapper.writeValueAsString(menuItem);
        // 創建Http request
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/menus/{menuId}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // 執行Http request
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}