package com.rajan.foodDeliveryApp.services.impl;

import com.rajan.foodDeliveryApp.domain.entities.FoodEntity;
import com.rajan.foodDeliveryApp.repositories.FoodRepository;
import com.rajan.foodDeliveryApp.services.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Override
    public FoodEntity save(FoodEntity foodEntity) {
        return foodRepository.save(foodEntity);
    }

    @Override
    public Optional<FoodEntity> findOne(Long id) {
        return foodRepository.findById(id);
    }

    @Override
    public Page<FoodEntity> findAllByMenu(Long id, Pageable pageable) {
        return foodRepository.findAllByMenuId(id, pageable);
    }

    @Override
    public Page<FoodEntity> findAll(Pageable pageable) {
        return foodRepository.findAll(pageable);
    }

    @Override
    public List<FoodEntity> findFoodsByRestaurantId(Long id) {
        return foodRepository.findFoodsByRestaurantId(id);
    }

    @Override
    public Optional<FoodEntity> findById(Long id) {
        return foodRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        foodRepository.deleteById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return foodRepository.existsById(id);
    }
}
