package com.rajan.foodDeliveryApp.services.impl;

import com.rajan.foodDeliveryApp.domain.dto.RecommendedFoodDto;
import com.rajan.foodDeliveryApp.domain.entities.FoodEntity;
import com.rajan.foodDeliveryApp.domain.entities.OrderDetailEntity;
import com.rajan.foodDeliveryApp.domain.entities.OrderEntity;
import com.rajan.foodDeliveryApp.domain.entities.RestaurantEntity;
import com.rajan.foodDeliveryApp.repositories.OrderRepository;
import com.rajan.foodDeliveryApp.services.FoodService;
import com.rajan.foodDeliveryApp.services.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    private final OrderRepository orderRepository;
    private final FoodService foodService;

    @Autowired
    public RecommendationServiceImpl(OrderRepository orderRepository, FoodService foodService) {
        this.orderRepository = orderRepository;
        this.foodService = foodService;
    }


    @Override
    public List<RecommendedFoodDto> recommendFoods(String weather) {
        // Key: foodId|restaurantId → Count
        Map<String, Integer> foodRestCountMap = new HashMap<>();
        Map<String, FoodEntity> foodMap = new HashMap<>();
        Map<String, RestaurantEntity> restaurantMap = new HashMap<>();

        AtomicInteger totalUsers = new AtomicInteger(0);

        List<OrderEntity> allOrders = orderRepository.findAll();

        List<OrderEntity> weatherMatchedOrders = allOrders.stream()
                .filter(order -> weather.equalsIgnoreCase(order.getWeather()))
                .collect(Collectors.toList());

        for (OrderEntity order : weatherMatchedOrders) {
            totalUsers.incrementAndGet();
            RestaurantEntity restaurant = order.getRestaurant();

            for (OrderDetailEntity detail : order.getOrderDetails()) {
                Long foodId = detail.getFoodId();
                Optional<FoodEntity> optionalFood = foodService.findById(foodId);

                if (optionalFood.isPresent()) {
                    FoodEntity food = optionalFood.get();
                    String key = food.getFoodId() + "|" + restaurant.getRestaurantId();

                    foodRestCountMap.merge(key, 1, Integer::sum);
                    foodMap.putIfAbsent(key, food);
                    restaurantMap.putIfAbsent(key, restaurant);
                }
            }
        }

        return foodRestCountMap.entrySet().stream()
                .map(entry -> {
                    String key = entry.getKey();
                    FoodEntity food = foodMap.get(key);
                    RestaurantEntity restaurant = restaurantMap.get(key);
                    double score = totalUsers.get() > 0 ? (double) entry.getValue() / totalUsers.get() : 0.0;

                    return new RecommendedFoodDto(food, restaurant, score);
                })
                .sorted((a, b) -> Double.compare(b.getScore(), a.getScore()))
                .collect(Collectors.toList());
    }


    /**
     * Process orders to count unique food+restaurant combinations per user.
     */
    private int processOrders(List<OrderEntity> orders,
                              Map<String, Integer> foodRestCountMap,
                              Map<String, RestaurantEntity> restaurantMap) {
        Map<Long, Set<String>> userToFoodRest = new HashMap<>();

        for (OrderEntity order : orders) {
            if (order.getUser() == null || order.getUser().getId() == null) continue;
            Long userId = order.getUser().getId();
            Set<String> foodRestSet = userToFoodRest.getOrDefault(userId, new HashSet<>());

            RestaurantEntity restaurant = order.getRestaurant();

            for (OrderDetailEntity detail : order.getOrderDetails()) {
                String foodName = detail.getFoodName();
                if (foodName != null && !foodName.isBlank()) {
                    String key = foodName + "|" + restaurant.getRestaurantId();
                    foodRestSet.add(key);
                    // Save restaurant for this key (only once)
                    restaurantMap.putIfAbsent(key, restaurant);
                }
            }

            userToFoodRest.put(userId, foodRestSet);
        }

        // Update counts for each unique food+restaurant combo per user
        for (Set<String> foodRestSet : userToFoodRest.values()) {
            for (String key : foodRestSet) {
                foodRestCountMap.put(key, foodRestCountMap.getOrDefault(key, 0) + 1);
            }
        }

        return userToFoodRest.size();
    }
}
