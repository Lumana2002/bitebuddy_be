package com.rajan.foodDeliveryApp.mappers.impl;

import com.rajan.foodDeliveryApp.domain.dto.RestaurantDto;
import com.rajan.foodDeliveryApp.domain.entities.RestaurantEntity;
import com.rajan.foodDeliveryApp.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMapperImpl implements Mapper<RestaurantEntity, RestaurantDto> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public RestaurantDto mapTo(RestaurantEntity restaurantEntity) {
        return modelMapper.map(restaurantEntity, RestaurantDto.class);
    }

    @Override
    public RestaurantEntity mapFrom(RestaurantDto restaurantDto) {
        return modelMapper.map(restaurantDto, RestaurantEntity.class);
    }
}
