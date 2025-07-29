package com.rajan.foodDeliveryApp.repositories;

import com.rajan.foodDeliveryApp.domain.entities.MenuEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, Long> {
    Page<MenuEntity> findByRestaurantRestaurantId(Long restaurantId, Pageable pageable);

    List<MenuEntity> findByRestaurantRestaurantId(Long restaurantId);


}
