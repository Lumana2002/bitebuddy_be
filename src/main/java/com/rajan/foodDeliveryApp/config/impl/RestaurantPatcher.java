package com.rajan.foodDeliveryApp.config.impl;

import com.rajan.foodDeliveryApp.config.Patcher;
import com.rajan.foodDeliveryApp.domain.entities.RestaurantEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class RestaurantPatcher implements Patcher<RestaurantEntity> {
    private static final Logger log = LoggerFactory.getLogger(RestaurantPatcher.class);

    @Override
    public void patch(RestaurantEntity existingRestaurant, RestaurantEntity incompleteRestaurant) throws IllegalAccessException {
        Class<?> restaurantEntityClass = RestaurantEntity.class;
        Field[] restaurantFields = restaurantEntityClass.getDeclaredFields();
        for (Field field : restaurantFields) {
            field.setAccessible(true);
            Object value = field.get(incompleteRestaurant);
            if ("image".equals(field.getName()) && value == null) {
                log.info(field.getName());
                field.set(existingRestaurant, null);
            } else if (value != null) {
                field.set(existingRestaurant, value);
            }
            field.setAccessible(false);
        }
    }
}
