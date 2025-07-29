package com.rajan.foodDeliveryApp.mappers.impl;

import com.rajan.foodDeliveryApp.domain.dto.OrderDto;
import com.rajan.foodDeliveryApp.domain.entities.OrderEntity;
import com.rajan.foodDeliveryApp.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderMapperImpl implements Mapper<OrderEntity, OrderDto> {

    private final ModelMapper modelMapper;

    @Autowired
    public OrderMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public OrderDto mapTo(OrderEntity orderEntity) {
        return modelMapper.map(orderEntity, OrderDto.class);
    }

    @Override
    public OrderEntity mapFrom(OrderDto orderDto) {
        return modelMapper.map(orderDto, OrderEntity.class);
    }
}
