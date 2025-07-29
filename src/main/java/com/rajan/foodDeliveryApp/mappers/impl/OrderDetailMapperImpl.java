package com.rajan.foodDeliveryApp.mappers.impl;

import com.rajan.foodDeliveryApp.domain.dto.OrderDetailDto;
import com.rajan.foodDeliveryApp.domain.entities.OrderDetailEntity;
import com.rajan.foodDeliveryApp.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderDetailMapperImpl implements Mapper<OrderDetailEntity, OrderDetailDto> {

    private final ModelMapper modelMapper;

    @Autowired
    public OrderDetailMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public OrderDetailDto mapTo(OrderDetailEntity orderDetailEntity) {
        return modelMapper.map(orderDetailEntity, OrderDetailDto.class);
    }

    @Override
    public OrderDetailEntity mapFrom(OrderDetailDto orderDetailDto) {
        return modelMapper.map(orderDetailDto, OrderDetailEntity.class);
    }
}

