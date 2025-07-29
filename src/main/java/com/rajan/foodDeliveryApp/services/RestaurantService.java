package com.rajan.foodDeliveryApp.services;

import com.rajan.foodDeliveryApp.domain.entities.RestaurantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RestaurantService {
    RestaurantEntity save(RestaurantEntity restaurantEntity);

    RestaurantEntity save(RestaurantEntity restaurantEntity, Long id);

    Optional<RestaurantEntity> findOne(Long id);

    Optional<RestaurantEntity> findByEmail(String email);

    Page<RestaurantEntity> findAll(Pageable pageable);

    void delete(Long id);

    boolean isExists(Long id);

    String encodeImage(byte[] imageData);

    void updateAveragePrice(Long menuId);
}
