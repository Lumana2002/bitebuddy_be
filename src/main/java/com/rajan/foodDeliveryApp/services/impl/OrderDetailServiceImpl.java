package com.rajan.foodDeliveryApp.services.impl;

import com.rajan.foodDeliveryApp.domain.entities.OrderDetailEntity;
import com.rajan.foodDeliveryApp.repositories.OrderDetailRepository;
import com.rajan.foodDeliveryApp.services.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;

    @Autowired
    public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }

    @Override
    public OrderDetailEntity save(OrderDetailEntity orderDetailEntity) {
        if (orderDetailEntity.getOrderId() == null) {
            return null;
        }
        return orderDetailRepository.save(orderDetailEntity);
    }

    @Override
    public List<OrderDetailEntity> saveAll(List<OrderDetailEntity> orderDetails) {
        for (OrderDetailEntity orderDetailEntity : orderDetails) {
            if (orderDetailEntity.getOrderId() == null){
                System.out.println(orderDetailEntity.getOrderId());
                return null;
            }
        }
        return orderDetailRepository.saveAll(orderDetails);
    }

    @Override
    public Page<OrderDetailEntity> findALl(Pageable pageable, Long orderId) {
        return null;
    }
}
