//package com.rajan.foodDeliveryApp.controllers;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.rajan.foodDeliveryApp.TestDataUtil;
//import com.rajan.foodDeliveryApp.domain.dto.RestaurantDto;
//import com.rajan.foodDeliveryApp.domain.entities.RestaurantEntity;
//import com.rajan.foodDeliveryApp.services.RestaurantService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.transaction.annotation.Transactional;
//
//@SpringBootTest
//@ExtendWith(SpringExtension.class)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//@AutoConfigureMockMvc
//public class RestaurantControllerIntegrationTests {
//
//    private static final Logger log = LoggerFactory.getLogger(RestaurantControllerIntegrationTests.class);
//    private final RestaurantService restaurantService;
//
//    private final ObjectMapper objectMapper;
//
//    private final MockMvc mockMvc;
//
//    @Autowired
//    public RestaurantControllerIntegrationTests(RestaurantService restaurantService, ObjectMapper objectMapper, MockMvc mockMvc) {
//        this.restaurantService = restaurantService;
//        this.objectMapper = objectMapper;
//        this.mockMvc = mockMvc;
//    }
//
//    @Test
//    public void testThatCreateRestaurantReturnsHttp201Created() throws Exception {
//        RestaurantDto restaurantDto = TestDataUtil.createTestRestaurantDtoA();
//        String restaurantJson = objectMapper.writeValueAsString(restaurantDto);
//
//        mockMvc.perform(
//                MockMvcRequestBuilders.post("/api/restaurants")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(restaurantJson)
//        ).andExpect(
//                MockMvcResultMatchers.status().isCreated()
//        );
//    }
//
//    @Test
//    public void testThatCreateRestaurantReturnsRestaurant() throws Exception {
//        RestaurantDto restaurantDto = TestDataUtil.createTestRestaurantDtoA();
//        String restaurantJson = objectMapper.writeValueAsString(restaurantDto);
//
//        mockMvc.perform(
//                        MockMvcRequestBuilders.post("/api/restaurants")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(restaurantJson)
//                ).andExpect(MockMvcResultMatchers.jsonPath("$.restaurant_id").isNumber())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Ram Momo"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.cuisine").value("Nepali"));
//    }
//
//    @Test
//    @Transactional
//    public void testThatListRestaurantReturnsHttp200OK() throws Exception {
//        RestaurantEntity restaurantEntityA = TestDataUtil.createTestRestaurantA();
//        restaurantService.save(restaurantEntityA);
//
//        RestaurantEntity restaurantEntityB = TestDataUtil.createTestRestaurantB();
//        restaurantService.save(restaurantEntityB);
//
//        mockMvc.perform(
//                MockMvcRequestBuilders.get("/api/restaurants")
//                        .contentType(MediaType.APPLICATION_JSON)
//        ).andExpect(
//                MockMvcResultMatchers.status().isOk()
//        );
//    }
//
//    @Test
//    @Transactional
//    public void testThatListRestaurantReturnsRestaurants() throws Exception {
//        RestaurantEntity restaurantEntityA = TestDataUtil.createTestRestaurantA();
//        restaurantService.save(restaurantEntityA);
//
//        mockMvc.perform(
//                        MockMvcRequestBuilders.get("/api/restaurants")
//                                .contentType(MediaType.APPLICATION_JSON)
//                ).andExpect(MockMvcResultMatchers.jsonPath("$.[2].restaurant_id").isNumber())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.[2].name").value("Ram Momo"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.[2].cuisine").value("Nepali"));
//    }
//
//    @Test
//    @Transactional
//    public void testThatGetRestaurantReturnsHttp200OK() throws Exception {
//        RestaurantEntity restaurantEntityA = TestDataUtil.createTestRestaurantA();
//        restaurantService.save(restaurantEntityA);
//
//        mockMvc.perform(
//                MockMvcRequestBuilders.get("/api/restaurants/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//        ).andExpect(
//                MockMvcResultMatchers.status().isOk()
//        );
//    }
//
//    @Test
//    @Transactional
//    public void testThatGetRestaurantReturnsRestaurant() throws Exception {
//        RestaurantEntity restaurantEntityA = TestDataUtil.createTestRestaurantA();
//        restaurantEntityA.setRestaurantId(100L);
//        restaurantService.save(restaurantEntityA);
//
//        mockMvc.perform(
//                        MockMvcRequestBuilders.get("/api/restaurants/1")
//                                .contentType(MediaType.APPLICATION_JSON)
//                ).andExpect(MockMvcResultMatchers.jsonPath("$.restaurant_id").isNumber())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Alchemy Pizzeria"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.cuisine").value("Italian"));
//    }
//
//    @Test
//    @Transactional
//    public void testThatUpdateRestaurantReturnsHttp200OK() throws Exception {
//        RestaurantEntity restaurantEntity = TestDataUtil.createTestRestaurantA();
//        restaurantService.save(restaurantEntity);
//
//        RestaurantDto restaurantDto = TestDataUtil.createTestRestaurantDtoA();
//        restaurantDto.setRestaurantId(restaurantEntity.getRestaurantId());
//        restaurantDto.setName("damn momo");
//
//        String restaurantJson = objectMapper.writeValueAsString(restaurantDto);
//
//        mockMvc.perform(
//                MockMvcRequestBuilders.put("/api/restaurants/" + restaurantEntity.getRestaurantId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(restaurantJson)
//        ).andExpect(
//                MockMvcResultMatchers.status().isOk()
//        );
//    }
//
//    @Test
//    @Transactional
//    public void testThatUpdateRestaurantReturnsUpdatedRestaurant() throws Exception {
//        RestaurantEntity restaurantEntity = TestDataUtil.createTestRestaurantA();
//        restaurantService.save(restaurantEntity);
//
//        RestaurantDto restaurantDto = TestDataUtil.createTestRestaurantDtoA();
//        restaurantDto.setRestaurantId(restaurantEntity.getRestaurantId());
//        restaurantDto.setName("Parvati momo");
//
//        String restaurantJson = objectMapper.writeValueAsString(restaurantDto);
//
//        mockMvc.perform(
//                        MockMvcRequestBuilders.put("/api/restaurants/" + restaurantEntity.getRestaurantId())
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(restaurantJson)
//                ).andExpect(MockMvcResultMatchers.jsonPath("$.restaurant_id").value(restaurantEntity.getRestaurantId()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Parvati momo"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.cuisine").value("Nepali"));
//    }
//
//    @Test
//    @Transactional
//    public void testThatDeleteRestaurantReturnsHttp204() throws Exception {
//        RestaurantEntity restaurantEntity = TestDataUtil.createTestRestaurantA();
//        restaurantService.save(restaurantEntity);
//
//        mockMvc.perform(
//                MockMvcRequestBuilders.delete("/api/restaurants/" + restaurantEntity.getRestaurantId())
//                        .contentType(MediaType.APPLICATION_JSON)
//        ).andExpect(
//                MockMvcResultMatchers.status().isNoContent());
//    }
//
//    @Test
//    @Transactional
//    public void testThatDeleteRestaurantReturnsHttp404WhenRestaurantNotFound() throws Exception {
//        mockMvc.perform(
//                MockMvcRequestBuilders.delete("/api/restaurants/9999")
//                        .contentType(MediaType.APPLICATION_JSON)
//        ).andExpect(MockMvcResultMatchers.status().isNotFound());
//    }
//}
