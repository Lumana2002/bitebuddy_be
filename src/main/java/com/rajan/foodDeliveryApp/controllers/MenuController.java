package com.rajan.foodDeliveryApp.controllers;


import com.rajan.foodDeliveryApp.domain.dto.FoodDto;
import com.rajan.foodDeliveryApp.domain.dto.MenuDto;
import com.rajan.foodDeliveryApp.domain.entities.FoodEntity;
import com.rajan.foodDeliveryApp.domain.entities.MenuEntity;
import com.rajan.foodDeliveryApp.domain.entities.RestaurantEntity;
import com.rajan.foodDeliveryApp.mappers.Mapper;
import com.rajan.foodDeliveryApp.services.FoodService;
import com.rajan.foodDeliveryApp.services.MenuService;
import com.rajan.foodDeliveryApp.services.RestaurantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MenuController {

    private static final Logger log = LoggerFactory.getLogger(MenuController.class);
    private final Mapper<MenuEntity, MenuDto> menuMapper;
    private final Mapper<FoodEntity, FoodDto> foodMapper;
    private final MenuService menuService;
    private final RestaurantService restaurantService;
    private final FoodService foodService;

    @Autowired
    public MenuController(RestaurantService restaurantService, Mapper<MenuEntity, MenuDto> menuMapper, Mapper<FoodEntity, FoodDto> foodMapper, MenuService menuService, FoodService foodService) {
        this.restaurantService = restaurantService;
        this.menuMapper = menuMapper;
        this.foodMapper = foodMapper;
        this.menuService = menuService;
        this.foodService = foodService;
    }

    @PreAuthorize("hasRole('ROLE_RESTAURANT')")
    @PostMapping(path = "/restaurants/{restaurant_id}/menus")
    public ResponseEntity<MenuDto> createMenu(
            @PathVariable("restaurant_id") Long restaurant_id,
            @RequestBody MenuDto menuDto) {
        MenuEntity menuEntity = menuMapper.mapFrom(menuDto);

        Optional<RestaurantEntity> optionalRestaurantEntity = restaurantService.findOne(restaurant_id);
        RestaurantEntity restaurantEntity = optionalRestaurantEntity.orElseThrow(() -> new RuntimeException("Restaurant not found"));
        /* TODO create a menu first then add food on another page,
            create menu then addFood takes in one food at a time. (requires a lot of requests)
            eg: list of 20 foods, 20 requests for one restaurant, 10 restaurant * 20 requests = 200 requests = server overhead
            or take a list and add each item to the db. (bulk entry)
        * */
        menuEntity.setRestaurant(restaurantEntity);


        MenuEntity savedMenuEntity = menuService.save(menuEntity);
        MenuDto savedMenuDto = menuMapper.mapTo(savedMenuEntity);
        return new ResponseEntity<>(savedMenuDto, HttpStatus.CREATED);
    }

//    @PreAuthorize("hasRole('ROLE_RESTAURANT')")
//    @PatchMapping(path = "/restaurants/{restaurant_id}/menus/{id}")
//    public ResponseEntity<MenuDto> updateMenu(@PathVariable("id") Long id, @PathVariable("restaurant_id") Long restaurant_id, @RequestBody MenuDto menuDto) {
//        if (!menuService.isExists(id)) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        MenuEntity menuEntity = menuMapper.mapFrom(menuDto);
//        if (!(menuEntity.getRestaurant().getRestaurantId().equals(restaurant_id))) {
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }
//
//        MenuEntity savedMenuEntity = menuService.save(menuEntity);
//        MenuDto savedMenuDto = menuMapper.mapTo(menuEntity);
//        return new ResponseEntity<>(savedMenuDto, HttpStatus.OK);
//    }

    //    @PreAuthorize("hasRole('ROLE_RESTAURANT')")
    @PatchMapping(path = "/restaurants/{restaurant_id}/menus/{id}")
    public ResponseEntity<MenuDto> addFoodToMenu(
            @PathVariable("id") Long id,
            @PathVariable("restaurant_id") Long restaurantId,
            @RequestBody MenuDto menuDto) {

        RestaurantEntity restaurantEntity = restaurantService.findOne(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));


        MenuEntity menuEntity = menuService.findOne(id)
                .orElseThrow(() -> new RuntimeException("Menu not found"));


        menuEntity.setMenuId(id);
        menuEntity.setRestaurant(restaurantEntity);

        FoodDto foodDto = menuDto.getFoods().get(0);
        FoodEntity foodEntity;

        if (foodDto.getFoodId() == null) {
            foodEntity = foodMapper.mapFrom(foodDto);
            foodEntity.setMenuId(id);
            foodEntity = foodService.save(foodEntity);
        } else {
            foodEntity = foodService.findById(foodDto.getFoodId())
                    .orElseThrow(() -> new RuntimeException("Food not found: " + foodDto.getFoodId()));
        }

        List<FoodEntity> currentFoods = menuEntity.getFoods();
        currentFoods.add(foodEntity);

        menuEntity.setFoods(currentFoods);
        MenuEntity savedMenuEntity = menuService.save(menuEntity);

        MenuDto savedMenuDto = menuMapper.mapTo(savedMenuEntity);
        return new ResponseEntity<>(savedMenuDto, HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "/menus")
    public Page<MenuDto> listMenus(Pageable pageable) {
        Page<MenuEntity> menuEntities = menuService.findAll(pageable);
        return menuEntities.map(menuMapper::mapTo);
    }

    @GetMapping(path = "/menus/{id}")
    public ResponseEntity<MenuDto> getMenu(@PathVariable("id") Long id) {
        Optional<MenuEntity> optionalMenuEntity = menuService.findOne(id);
        MenuEntity menuEntity = optionalMenuEntity.orElseThrow(() -> new IllegalArgumentException("Could not find Menu"));
        MenuDto menuDto = menuMapper.mapTo(menuEntity);
        return new ResponseEntity<>(menuDto, HttpStatus.OK);
    }


    @GetMapping(path = "/restaurants/{restaurant_id}/menus")
    public Page<MenuDto> listRestaurantMenus(@PathVariable("restaurant_id") Long restaurant_id, Pageable pageable) {
        Page<MenuEntity> restaurantMenuList = menuService.getMenusByRestaurantId(restaurant_id, pageable);
        return restaurantMenuList.map(menuMapper::mapTo);
    }
}
