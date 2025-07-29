package com.rajan.foodDeliveryApp.services;

import com.rajan.foodDeliveryApp.domain.entities.FoodEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface FoodService {

    FoodEntity save(FoodEntity foodEntity);

    Optional<FoodEntity> findOne(Long id);

    Page<FoodEntity> findAllByMenu(Long id, Pageable pageable);

    Page<FoodEntity> findAll(Pageable pageable);

    List<FoodEntity> findFoodsByRestaurantId(Long id);

    Optional<FoodEntity> findById(Long id);

    void delete(Long id);

    boolean isExists(Long id);

}
