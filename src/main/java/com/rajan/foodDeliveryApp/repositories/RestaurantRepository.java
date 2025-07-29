package com.rajan.foodDeliveryApp.repositories;

import com.rajan.foodDeliveryApp.domain.entities.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
    Optional<RestaurantEntity> findByEmail(String email);
}
