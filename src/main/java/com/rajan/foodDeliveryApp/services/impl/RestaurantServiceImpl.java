package com.rajan.foodDeliveryApp.services.impl;

import com.rajan.foodDeliveryApp.config.impl.RestaurantPatcher;
import com.rajan.foodDeliveryApp.domain.entities.FoodEntity;
import com.rajan.foodDeliveryApp.domain.entities.RestaurantEntity;
import com.rajan.foodDeliveryApp.repositories.RestaurantRepository;
import com.rajan.foodDeliveryApp.services.FoodService;
import com.rajan.foodDeliveryApp.services.MenuService;
import com.rajan.foodDeliveryApp.services.RestaurantService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {


    private final RestaurantRepository restaurantRepository;
    private final MenuService menuService;
    private final FoodService foodService;
    private final RestaurantPatcher patcher;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, RestaurantPatcher patcher, MenuService menuService, FoodService foodService) {
        this.restaurantRepository = restaurantRepository;
        this.patcher = patcher;
        this.menuService = menuService;
        this.foodService = foodService;
    }

    @Override
    public RestaurantEntity save(RestaurantEntity restaurantEntity) {
        return restaurantRepository.save(restaurantEntity);
    }

    @Override
    public RestaurantEntity save(RestaurantEntity restaurantEntity, Long id) {

        restaurantEntity.setRestaurantId(id);
        RestaurantEntity existingRestaurant = findOne(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found with id: " + id));

        try {
            patcher.patch(existingRestaurant, restaurantEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return restaurantRepository.save(existingRestaurant);
    }

    @Override
    public Optional<RestaurantEntity> findOne(Long id) {
        return restaurantRepository.findById(id);
    }

    @Override
    public Optional<RestaurantEntity> findByEmail(String email) {
        return restaurantRepository.findByEmail(email);
    }

    @Override
    public Page<RestaurantEntity> findAll(Pageable pageable) {
        return restaurantRepository.findAll(pageable);
    }

    @Override
    public void delete(Long id) {
        restaurantRepository.deleteById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return restaurantRepository.existsById(id);
    }

    @Override
    public String encodeImage(byte[] imageData) {
        return Base64.getEncoder().encodeToString(imageData);
    }

    @Override
    public void updateAveragePrice(Long menuId) {
        Long restaurantId = menuService.findOne(menuId)
                .orElseThrow(() -> new EntityNotFoundException("Menu not found"))
                .getRestaurant().getRestaurantId();

        List<FoodEntity> foods = foodService.findFoodsByRestaurantId(restaurantId);

        RestaurantEntity restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));

        if (foods.isEmpty()) {
            restaurant.setAveragePrice(0.0);
        } else {
            double totalPrice = foods.stream()
                    .map(FoodEntity::getPrice)
                    .mapToDouble(BigDecimal::doubleValue)
                    .sum();

            double averagePrice = totalPrice / foods.size();
            restaurant.setAveragePrice(averagePrice);
        }

        restaurantRepository.save(restaurant);
    }

}
