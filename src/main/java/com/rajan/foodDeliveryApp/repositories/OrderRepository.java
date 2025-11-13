package com.rajan.foodDeliveryApp.repositories;

import com.rajan.foodDeliveryApp.domain.dto.OrderDto;
import com.rajan.foodDeliveryApp.domain.entities.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Page<OrderEntity> findAllByUserId(Pageable pageable, Long userId);

    List<OrderEntity> findByWeather(String weather);

    Page<OrderEntity> findAllByRestaurant_RestaurantId(Pageable pageable, Long restaurantId);
}
