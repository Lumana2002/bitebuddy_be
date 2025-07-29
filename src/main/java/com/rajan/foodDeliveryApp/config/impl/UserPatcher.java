package com.rajan.foodDeliveryApp.config.impl;

import com.rajan.foodDeliveryApp.config.Patcher;
import com.rajan.foodDeliveryApp.domain.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class UserPatcher implements Patcher<UserEntity> {
    @Override
    public void patch(UserEntity existingUser, UserEntity incompleteUser) throws IllegalAccessException {
        Class<?> userEntityClass = UserEntity.class;
        Field[] userFields = userEntityClass.getDeclaredFields();
        for (Field field : userFields) {
            field.setAccessible(true);
            Object value = field.get(incompleteUser);
            if (value != null) {
                field.set(existingUser, value);
            }
            field.setAccessible(false);
        }
    }
}
