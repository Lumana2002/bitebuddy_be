package com.rajan.foodDeliveryApp.mappers.impl;

import com.rajan.foodDeliveryApp.domain.dto.FoodDto;
import com.rajan.foodDeliveryApp.domain.entities.FoodEntity;
import com.rajan.foodDeliveryApp.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FoodMapperImpl implements Mapper<FoodEntity, FoodDto> {

    private final ModelMapper modelMapper;

    @Autowired
    public FoodMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public FoodDto mapTo(FoodEntity foodEntity) {
        return modelMapper.map(foodEntity, FoodDto.class);
    }

    @Override
    public FoodEntity mapFrom(FoodDto foodDto) {
        return modelMapper.map(foodDto, FoodEntity.class);
    }
}
