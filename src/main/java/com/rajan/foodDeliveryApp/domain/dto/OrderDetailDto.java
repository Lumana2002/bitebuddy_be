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
public class OrderDetailDto {
    private Long id;
    private Long foodId;
    private Long orderId;
    private String foodName;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;
//    private String notes;
//    private String orderDetailStatus;
}
