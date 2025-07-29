package com.rajan.foodDeliveryApp.mappers.impl;

import com.rajan.foodDeliveryApp.domain.dto.UserDto;
import com.rajan.foodDeliveryApp.domain.entities.UserEntity;
import com.rajan.foodDeliveryApp.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements Mapper<UserEntity, UserDto> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto mapTo(UserEntity userEntity) {
        return modelMapper.map(userEntity,UserDto.class);
    }

    @Override
    public UserEntity mapFrom(UserDto userDto) {
        return modelMapper.map(userDto,UserEntity.class);
    }
}
