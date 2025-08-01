package com.rajan.foodDeliveryApp.controllers;

import com.rajan.foodDeliveryApp.domain.dto.RecommendedFoodDto;
import com.rajan.foodDeliveryApp.domain.entities.OrderEntity;
import com.rajan.foodDeliveryApp.domain.entities.RestaurantEntity;
import com.rajan.foodDeliveryApp.repositories.RestaurantRepository;
import com.rajan.foodDeliveryApp.services.ContentBasedRecommenderService;
import com.rajan.foodDeliveryApp.services.RecommendationService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;
    private final ContentBasedRecommenderService recommenderService;
    private final RestaurantRepository restaurantRepository;

    public RecommendationController(
            RecommendationService recommendationService,
            ContentBasedRecommenderService recommenderService,
            RestaurantRepository restaurantRepository
    ) {
        this.recommendationService = recommendationService;
        this.recommenderService = recommenderService;
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping
    public List<RecommendedFoodDto> recommend(
            @RequestParam String weather) {
        return recommendationService.recommendFoods(weather);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/restaurant")
    public List<RestaurantEntity> recommendRestaurant(@RequestBody List<OrderEntity> userOrders) {
        List<RestaurantEntity> allRestaurants = restaurantRepository.findAll();
        return recommenderService.recommendRestaurant(allRestaurants, userOrders);
    }
}
