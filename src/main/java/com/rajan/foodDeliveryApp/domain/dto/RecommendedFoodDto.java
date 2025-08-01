package com.rajan.foodDeliveryApp.domain.dto;

import com.rajan.foodDeliveryApp.domain.entities.FoodEntity;
import com.rajan.foodDeliveryApp.domain.entities.RestaurantEntity;

public class RecommendedFoodDto {
    private FoodEntity food;
    private RestaurantEntity restaurant;
    private double score;

    public RecommendedFoodDto(FoodEntity food, RestaurantEntity restaurant, double score) {
        this.food = food;
        this.restaurant = restaurant;
        this.score = score;
    }

    public FoodEntity getFood() {
        return food;
    }

    public void setFood(FoodEntity food) {
        this.food = food;
    }

    public RestaurantEntity getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantEntity restaurant) {
        this.restaurant = restaurant;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
