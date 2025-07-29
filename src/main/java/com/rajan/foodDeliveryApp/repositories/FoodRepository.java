package com.rajan.foodDeliveryApp.repositories;

import com.rajan.foodDeliveryApp.domain.entities.FoodEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<FoodEntity, Long> {
    Page<FoodEntity> findAllByMenuId(Long id, Pageable page);

    @Query("SELECT f FROM FoodEntity f WHERE f.menuId IN (SELECT m.menuId FROM MenuEntity m WHERE m.restaurant.restaurantId = :restaurantId)")
    List<FoodEntity> findFoodsByRestaurantId(@Param("restaurantId") Long restaurantId);
}
