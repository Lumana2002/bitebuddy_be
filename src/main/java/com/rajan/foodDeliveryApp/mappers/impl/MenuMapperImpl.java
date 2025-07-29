package com.rajan.foodDeliveryApp.mappers.impl;

import com.rajan.foodDeliveryApp.domain.dto.MenuDto;
import com.rajan.foodDeliveryApp.domain.entities.MenuEntity;
import com.rajan.foodDeliveryApp.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MenuMapperImpl implements Mapper<MenuEntity, MenuDto> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public MenuDto mapTo(MenuEntity menuEntity) {
        return modelMapper.map(menuEntity, MenuDto.class);
    }

    @Override
    public MenuEntity mapFrom(MenuDto menuDto) {
        return modelMapper.map(menuDto, MenuEntity.class);
    }
}
