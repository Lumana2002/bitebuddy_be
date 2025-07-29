package com.rajan.foodDeliveryApp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantDto {
    private Long restaurantId;
    private String name;
    private String email;
    private String cuisine;
    private String contact;
    private String image;
    private String address;
    private String description;
    private String status;
    private Integer averagePrice;
}
