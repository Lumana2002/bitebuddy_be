package com.rajan.foodDeliveryApp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuDto {
    private Long menuId;
    private String name;
    private RestaurantDto restaurant;
    private List<FoodDto> foods;
}
