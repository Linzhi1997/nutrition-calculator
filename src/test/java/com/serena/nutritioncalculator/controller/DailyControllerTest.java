package com.serena.nutritioncalculator.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.serena.nutritioncalculator.constant.MealType;
import com.serena.nutritioncalculator.dao.DailyDao;
import com.serena.nutritioncalculator.dao.FoodDao;
import com.serena.nutritioncalculator.dao.Impl.DailyDaoImpl;
import com.serena.nutritioncalculator.dao.MenuDao;
import com.serena.nutritioncalculator.dao.UserDao;
import com.serena.nutritioncalculator.dto.MenuItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class DailyControllerTest {

    @Autowired
    MenuDao menuDao;

    @Autowired
    FoodDao foodDao;

    @Autowired
    DailyDao dailyDao;

    @Autowired
    MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    @DirtiesContext
    @Test
    public void update_success() throws Exception{

        // 創建Http request
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users/{userId}/dailys/today",2)
                .param("recommendCal","1700");

        // 執行Http request
        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.dailyId", notNullValue()))
                .andExpect(jsonPath("$.userId", equalTo(2)))
                .andExpect(jsonPath("$.recommendCal",equalTo(1700)))
                .andExpect(jsonPath("$.dailyCal",equalTo(0)))
                .andExpect(jsonPath("$.dailyCarbs",equalTo(0)))
                .andExpect(jsonPath("$.dailyProtein",equalTo(0)))
                .andExpect(jsonPath("$.dailyFat",equalTo(0)))
                .andExpect(jsonPath("$.achievePercent",equalTo("0%")))
                .andExpect(jsonPath("$.dailyBeginTime",notNullValue()))
                .andExpect(jsonPath("$.dailyLastModifiedDate",notNullValue()));

    }

    @Transactional
    @DirtiesContext
    @Test
    public void update_whenMenuCreate() throws Exception{
        // 創建 MenuCreate Http request
        createMenu(2);
        // 創建 Daily Http request
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users/{userId}/dailys/today",2)
                .param("recommendCal","1700");
        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.dailyId", notNullValue()))
                .andExpect(jsonPath("$.userId", equalTo(2)))
                .andExpect(jsonPath("$.recommendCal",equalTo(1700)))
                .andExpect(jsonPath("$.dailyCal",equalTo(513)))
                .andExpect(jsonPath("$.dailyCarbs",equalTo(73)))
                .andExpect(jsonPath("$.dailyProtein",equalTo(34)))
                .andExpect(jsonPath("$.dailyFat",equalTo(10)))
                .andExpect(jsonPath("$.achievePercent",equalTo("30%")))
                .andExpect(jsonPath("$.dailyBeginTime",notNullValue()))
                .andExpect(jsonPath("$.dailyLastModifiedDate",notNullValue()));
    }

    @Transactional
    @DirtiesContext
    @Test
    public void update_whenMenuUpdate() throws Exception{
        // 創建 MenuCreate Http request
        createMenu(2);
        // 再updateMenu
        MenuItem menuItem = new MenuItem();
        menuItem.setMealType(MealType.L);
        menuItem.setFoodId(4);
        menuItem.setExchange(2);

        String json = objectMapper.writeValueAsString(menuItem);

        // 創建 MenuUpdate Http request
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/menus/{menuId}",10)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(requestBuilder);

        // 創建 Daily Http request
        RequestBuilder requestBuilder2 = MockMvcRequestBuilders
                .get("/users/{userId}/dailys/today",2)
                .param("recommendCal","1700");

        mockMvc.perform(requestBuilder2)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.dailyId", notNullValue()))
                .andExpect(jsonPath("$.userId", equalTo(2)))
                .andExpect(jsonPath("$.recommendCal",equalTo(1700)))
                .andExpect(jsonPath("$.dailyCal",equalTo(1026)))
                .andExpect(jsonPath("$.dailyCarbs",equalTo(146)))
                .andExpect(jsonPath("$.dailyProtein",equalTo(68)))
                .andExpect(jsonPath("$.dailyFat",equalTo(20)))
                .andExpect(jsonPath("$.achievePercent",equalTo("60%")))
                .andExpect(jsonPath("$.dailyBeginTime",notNullValue()))
                .andExpect(jsonPath("$.dailyLastModifiedDate",notNullValue()));
    }

    @Transactional
    @DirtiesContext
    @Test
    public void update_whenMenuDelete() throws Exception{
        // 創建 MenuCreate Http request
        createMenu(2);

        // 創建 MenuUpdate Http request
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/menus/{menuId}",10);
        mockMvc.perform(requestBuilder);

        // 創建 Daily Http request
        RequestBuilder requestBuilder2 = MockMvcRequestBuilders
                .get("/users/{userId}/dailys/today",2)
                .param("recommendCal","1700");

        mockMvc.perform(requestBuilder2)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.dailyId", notNullValue()))
                .andExpect(jsonPath("$.userId", equalTo(2)))
                .andExpect(jsonPath("$.recommendCal",equalTo(1700)))
                .andExpect(jsonPath("$.dailyCal",equalTo(0)))
                .andExpect(jsonPath("$.dailyCarbs",equalTo(0)))
                .andExpect(jsonPath("$.dailyProtein",equalTo(0)))
                .andExpect(jsonPath("$.dailyFat",equalTo(0)))
                .andExpect(jsonPath("$.achievePercent",equalTo("0%")))
                .andExpect(jsonPath("$.dailyBeginTime",notNullValue()))
                .andExpect(jsonPath("$.dailyLastModifiedDate",notNullValue()));
    }

    @Test
    public void get_history_success() throws Exception {
        // 創建 Daily Http request
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users/{userId}/dailys",2)
                .param("beginDate","2024-09-01 00:00:00")
                .param("endDate","2024-09-15 00:00:00")
                .param("limit","10")
                .param("offset","0");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total",equalTo(2)))
                .andExpect(jsonPath("$.limit",equalTo(10)))
                .andExpect(jsonPath("$.offset",equalTo(0)))

                .andExpect(jsonPath("$.resultsList[0].dailyId", notNullValue()))
                .andExpect(jsonPath("$.resultsList[0].userId", equalTo(2)))
                .andExpect(jsonPath("$.resultsList[0].recommendCal", equalTo(1700)))
                .andExpect(jsonPath("$.resultsList[0].dailyCal", equalTo(1680)))
                .andExpect(jsonPath("$.resultsList[0].dailyCarbs", equalTo(210)))
                .andExpect(jsonPath("$.resultsList[0].dailyProtein", equalTo(80)))
                .andExpect(jsonPath("$.resultsList[0].dailyFat", equalTo(40)))
                .andExpect(jsonPath("$.resultsList[0].achievePercent", equalTo("99%")))
                .andExpect(jsonPath("$.resultsList[0].dailyBeginTime", notNullValue()))
                .andExpect(jsonPath("$.resultsList[0].dailyLastModifiedDate", notNullValue()))

                .andExpect(jsonPath("$.resultsList[1].dailyId", notNullValue()))
                .andExpect(jsonPath("$.resultsList[1].userId", equalTo(2)))
                .andExpect(jsonPath("$.resultsList[1].recommendCal", equalTo(1700)))
                .andExpect(jsonPath("$.resultsList[1].dailyCal", equalTo(1720)))
                .andExpect(jsonPath("$.resultsList[1].dailyCarbs", equalTo(220)))
                .andExpect(jsonPath("$.resultsList[1].dailyProtein", equalTo(85)))
                .andExpect(jsonPath("$.resultsList[1].dailyFat", equalTo(45)))
                .andExpect(jsonPath("$.resultsList[1].achievePercent", equalTo("101%")))
                .andExpect(jsonPath("$.resultsList[1].dailyBeginTime", notNullValue()))
                .andExpect(jsonPath("$.resultsList[1].dailyLastModifiedDate", notNullValue()));

    }

    @Test
    public void get_history_NoExistUser() throws Exception {
        // 創建 Daily Http request
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users/{userId}/dailys", 100)
                .param("beginDate", "2024-09-01 00:00:00")
                .param("endDate", "2024-09-15 00:00:00")
                .param("limit", "10")
                .param("offset", "0");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void get_history_invalidTimeDuration() throws Exception {
        // 創建 Daily Http request
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users/{userId}/dailys", 100)
                .param("beginDate", "2050-09-01 00:00:00")
                .param("endDate", "2050-09-15 00:00:00")
                .param("limit", "10")
                .param("offset", "0");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }


    private void createMenu(Integer userId) throws Exception {
        // 先createMenu
        MenuItem menuItem = new MenuItem();
        menuItem.setMealType(MealType.L);
        menuItem.setFoodId(4);
        menuItem.setExchange(1);

        String json = objectMapper.writeValueAsString(menuItem);

        // 創建 MenuCreate Http request
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/{userId}/menus",userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(requestBuilder);
    }
}