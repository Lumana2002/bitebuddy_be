package com.rajan.foodDeliveryApp.controllers;

import com.rajan.foodDeliveryApp.domain.dto.FoodDto;
import com.rajan.foodDeliveryApp.domain.entities.FoodEntity;
import com.rajan.foodDeliveryApp.mappers.Mapper;
import com.rajan.foodDeliveryApp.services.FoodService;
import com.rajan.foodDeliveryApp.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/foods")
public class FoodController {


    private final Mapper<FoodEntity, FoodDto> foodMapper;

    private final FoodService foodService;
    private final RestaurantService restaurantService;

    @Autowired
    public FoodController(Mapper<FoodEntity, FoodDto> foodMapper, FoodService foodService, RestaurantService restaurantService) {
        this.foodMapper = foodMapper;
        this.foodService = foodService;
        this.restaurantService = restaurantService;
    }

    @PreAuthorize("hasRole('ROLE_RESTAURANT')")
    @PostMapping(path = "")
    public ResponseEntity<FoodDto> createFood(@RequestBody FoodDto foodDto) {
        FoodEntity foodEntity = foodMapper.mapFrom(foodDto);
        FoodEntity savedFoodEntity = foodService.save(foodEntity);
        FoodDto savedFoodDto = foodMapper.mapTo(savedFoodEntity);
        restaurantService.updateAveragePrice(foodEntity.getMenuId());
        return new ResponseEntity<>(savedFoodDto, HttpStatus.CREATED);
    }

    //TODO restaurant specific listOfFoods required
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "")
    public Page<FoodDto> listFoods(Pageable pageable) {
        Page<FoodEntity> foodEntities = foodService.findAll(pageable);
        return foodEntities.map(foodMapper::mapTo);
    }

    @GetMapping(path = "/menu/{id}")
    public Page<FoodDto> listMenuFoods(@PathVariable("id") Long id, Pageable pageable) {
        Page<FoodEntity> foodEntities = foodService.findAllByMenu(id, pageable);
        return foodEntities.map(foodMapper::mapTo);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<FoodDto> getFood(@PathVariable("id") Long id) {
        Optional<FoodEntity> foundFood = foodService.findOne(id);
        return foundFood.map(foodEntity -> {
            FoodDto foodDto = foodMapper.mapTo(foodEntity);
            return new ResponseEntity<>(foodDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //TODO also need to require restaurant id that has the food instead of role
    @PreAuthorize("hasRole('ROLE_RESTAURANT')")
    @PutMapping(path = "/{id}")
    public ResponseEntity<FoodDto> updateFood(@PathVariable("id") Long id, @RequestBody FoodDto foodDto) {
        if (!foodService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        FoodEntity foodEntity = foodMapper.mapFrom(foodDto);
        foodEntity.setFoodId(id);
        FoodEntity savedFoodEntity = foodService.save(foodEntity);
        FoodDto savedFoodDto = foodMapper.mapTo(savedFoodEntity);
        restaurantService.updateAveragePrice(foodEntity.getMenuId());
        return new ResponseEntity<>(savedFoodDto, HttpStatus.OK);
    }

    //TODO also need to require restaurant id that has the food instead of role
    @PreAuthorize("hasRole('ROLE_RESTAURANT')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<FoodDto> deleteFood(@PathVariable("id") Long id) {
        if (!foodService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        foodService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
