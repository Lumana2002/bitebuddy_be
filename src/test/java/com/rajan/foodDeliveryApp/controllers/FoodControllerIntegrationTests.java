//package com.rajan.foodDeliveryApp.controllers;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.rajan.foodDeliveryApp.TestDataUtil;
//import com.rajan.foodDeliveryApp.domain.dto.FoodDto;
//import com.rajan.foodDeliveryApp.domain.dto.MenuDto;
//import com.rajan.foodDeliveryApp.services.FoodService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//@SpringBootTest
//@ExtendWith(SpringExtension.class)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//@AutoConfigureMockMvc
//public class FoodControllerIntegrationTests {
//
//    private final FoodService foodService;
//    private final ObjectMapper objectMapper;
//    private final MockMvc mockMvc;
//
//    @Autowired
//    public FoodControllerIntegrationTests(FoodService foodService, ObjectMapper objectMapper, MockMvc mockMvc) {
//        this.foodService = foodService;
//        this.objectMapper = objectMapper;
//        this.mockMvc = mockMvc;
//    }
//
//    @Test
//    public void testThatCreateFoodReturnsHttp201Created() throws Exception {
//        FoodDto foodDto = TestDataUtil.createTestFoodDtoA();
//        String foodDtoJson = objectMapper.writeValueAsString(foodDto);
//
//        mockMvc.perform(
//                MockMvcRequestBuilders.post("api/foods")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(foodDtoJson)
//        ).andExpect(
//                MockMvcResultMatchers.status().isCreated()
//        );
//    }
//}
