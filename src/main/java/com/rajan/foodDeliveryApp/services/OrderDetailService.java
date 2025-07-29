package com.rajan.foodDeliveryApp.services;

import com.rajan.foodDeliveryApp.domain.entities.OrderDetailEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface OrderDetailService {
    OrderDetailEntity save(OrderDetailEntity orderDetailEntity);

    List<OrderDetailEntity> saveAll(List<OrderDetailEntity> orderDetails);

    Page<OrderDetailEntity> findALl(Pageable pageable, Long orderId);
}
