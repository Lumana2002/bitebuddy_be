package com.rajan.foodDeliveryApp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private Long id;
    private Long restaurantId;
    private Long userId;
    private RestaurantDto restaurant;
    private UserDto user;
    private Long partnerId;
    private List<OrderDetailDto> orderDetails;
    private LocalDateTime orderDate;
    private String deliveryAddress;
    private BigDecimal totalPrice;
    private BigDecimal averagePrice;
    private String paymentStatus;
    private String orderStatus;
    private String specialInstructions;
    private String transactionId;
    private String cancellationReason;
    private LocalDateTime deliveryDate;
    private String notes;

}