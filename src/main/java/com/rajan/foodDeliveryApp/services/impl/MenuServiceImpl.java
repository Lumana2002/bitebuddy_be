package com.rajan.foodDeliveryApp.services.impl;

import com.rajan.foodDeliveryApp.domain.entities.MenuEntity;
import com.rajan.foodDeliveryApp.repositories.MenuRepository;
import com.rajan.foodDeliveryApp.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Override
    public MenuEntity save(MenuEntity menuEntity) {
        return menuRepository.save(menuEntity);
    }

    @Override
    public Optional<MenuEntity> findOne(Long id) {
        return menuRepository.findById(id);
    }

    @Override
    public Page<MenuEntity> findAll(Pageable pageable) {
        return menuRepository.findAll(pageable);
    }

    @Override
    public Page<MenuEntity> getMenusByRestaurantId(Long restaurantId, Pageable pageable) {
        return menuRepository.findByRestaurantRestaurantId(restaurantId, pageable);
    }

    @Override
    public List<MenuEntity> findByRestaurantRestaurantId(Long id) {
        return menuRepository.findByRestaurantRestaurantId(id);
    }

    @Override
    public void delete(Long id) {
        menuRepository.deleteById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return menuRepository.existsById(id);
    }
}
