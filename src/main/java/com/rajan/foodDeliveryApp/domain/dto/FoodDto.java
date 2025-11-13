package com.rajan.foodDeliveryApp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodDto {
    private Long foodId;
    private Long menuId;
    private String name;
    private Integer spiceLevel;
    private String category;
    private BigDecimal price;
}
