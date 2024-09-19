package com.serena.nutritioncalculator.controller;

import com.serena.nutritioncalculator.dao.FoodDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FoodControllerTest {

    @Autowired
    FoodDao foodDao;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void get_food_success() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/foods/{foodId}",1);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.foodName",equalTo("夯番薯100g")))
                .andExpect(jsonPath("$.foodCal",equalTo(127)))
                .andExpect(jsonPath("$.foodCarbs",equalTo(28)))
                .andExpect(jsonPath("$.foodProtein",equalTo(1)))
                .andExpect(jsonPath("$.foodFat",equalTo(0)))
                .andExpect(jsonPath("$.foodLocation",equalTo("超商")));
    }

    @Test
    public void get_foods_success() throws Exception {
        // 搜尋位置 超商/熱量>300的食材
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/foods")
                .param("foodLocation","CVS")
                .param("compare",">")
                .param("foodCal","300");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultsList[0].foodName",equalTo("烤蛋白餐盒")))
                .andExpect(jsonPath("$.resultsList[0].foodCal",equalTo(513)))
                .andExpect(jsonPath("$.resultsList[0].foodCarbs",equalTo(73)))
                .andExpect(jsonPath("$.resultsList[0].foodProtein",equalTo(34)))
                .andExpect(jsonPath("$.resultsList[0].foodFat",equalTo(10)))
                .andExpect(jsonPath("$.resultsList[0].foodLocation",equalTo("超商")))

                .andExpect(jsonPath("$.resultsList[1].foodName",equalTo("健身Ｇ肉餐盒")))
                .andExpect(jsonPath("$.resultsList[1].foodCal",equalTo(500)))
                .andExpect(jsonPath("$.resultsList[1].foodCarbs",equalTo(68)))
                .andExpect(jsonPath("$.resultsList[1].foodProtein",equalTo(29)))
                .andExpect(jsonPath("$.resultsList[1].foodFat",equalTo(12)))
                .andExpect(jsonPath("$.resultsList[1].foodLocation",equalTo("超商")));
    }

    @Test
    public void get_foods_wrongCompare() throws Exception {
        // 搜尋位置 超商/熱量>300的食材
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/foods")
                .param("foodLocation","CVS")
                .param("compare","adsdqwd")
                .param("foodCal","300");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }
}