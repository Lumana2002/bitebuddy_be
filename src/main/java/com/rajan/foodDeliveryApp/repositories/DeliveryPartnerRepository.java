package com.rajan.foodDeliveryApp.repositories;

import com.rajan.foodDeliveryApp.domain.entities.DeliveryPartnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryPartnerRepository extends JpaRepository<DeliveryPartnerEntity, Long> {
}
