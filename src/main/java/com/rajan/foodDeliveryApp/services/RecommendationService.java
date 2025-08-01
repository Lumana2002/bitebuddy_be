package com.rajan.foodDeliveryApp.services;

import com.rajan.foodDeliveryApp.domain.dto.RecommendedFoodDto;

import java.util.List;

public interface RecommendationService {
    List<RecommendedFoodDto> recommendFoods(String weather);
}
