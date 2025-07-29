package com.rajan.foodDeliveryApp.services.impl;

import com.rajan.foodDeliveryApp.domain.entities.DeliveryPartnerEntity;
import com.rajan.foodDeliveryApp.services.DeliveryPartnerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DeliveryPartnerServiceImpl implements DeliveryPartnerService {

    // TODO create repository and implement methods

    @Override
    public DeliveryPartnerEntity save(DeliveryPartnerEntity deliveryPartnerEntity) {
        return null;
    }

    @Override
    public Page<DeliveryPartnerEntity> findAll(Pageable pageable) {
        return null;
    }
}
