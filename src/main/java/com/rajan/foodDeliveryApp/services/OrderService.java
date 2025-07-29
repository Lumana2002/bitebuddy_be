package com.rajan.foodDeliveryApp.services;

import com.rajan.foodDeliveryApp.domain.entities.OrderEntity;
import com.rajan.foodDeliveryApp.domain.entities.RestaurantEntity;
import com.rajan.foodDeliveryApp.domain.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface OrderService {
    @Autowired
    WeatherService weatherService = null;

    OrderEntity save(OrderEntity orderEntity, UserEntity user, RestaurantEntity restaurant);

    OrderEntity save(OrderEntity orderEntity);

    Page<OrderEntity> findAll(Pageable pageable, Long userId);

    Optional<OrderEntity> findOne(Long id);

    boolean isExists(Long id);

}
