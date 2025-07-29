package com.rajan.foodDeliveryApp.controllers;

import com.rajan.foodDeliveryApp.domain.dto.RestaurantDto;
import com.rajan.foodDeliveryApp.domain.entities.RestaurantEntity;
import com.rajan.foodDeliveryApp.domain.entities.UserEntity;
import com.rajan.foodDeliveryApp.mappers.Mapper;
import com.rajan.foodDeliveryApp.mappers.impl.RestaurantMapperImpl;
import com.rajan.foodDeliveryApp.services.RestaurantService;
import com.rajan.foodDeliveryApp.services.UserService;
import com.rajan.foodDeliveryApp.services.impl.RestaurantServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/restaurants")
public class RestaurantController {

    private static final Logger log = LoggerFactory.getLogger(RestaurantController.class);
    private final Mapper<RestaurantEntity, RestaurantDto> restaurantMapper;

    private final RestaurantService restaurantService;
    private final UserService userService;

    @Autowired
    public RestaurantController(RestaurantMapperImpl restaurantMapper, RestaurantServiceImpl restaurantService, UserService userService) {
        this.restaurantMapper = restaurantMapper;
        this.restaurantService = restaurantService;
        this.userService = userService;
    }

    @GetMapping(path = "")
    public Page<RestaurantDto> listRestaurants(Pageable pageable, @RequestParam(defaultValue = "1") int page,
                                               @RequestParam(defaultValue = "30") int size) {
        Page<RestaurantEntity> restaurantEntityPage = restaurantService.findAll(pageable);
        return restaurantEntityPage.map(restaurantEntity -> {
            RestaurantDto restaurantDto = restaurantMapper.mapTo(restaurantEntity);
            restaurantDto.setImage(restaurantEntity.getImage());
            return restaurantDto;
        });
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<RestaurantDto> getRestaurant(@PathVariable Long id) {
        Optional<RestaurantEntity> foundRestaurant = restaurantService.findOne(id);
        return foundRestaurant.map(restaurantEntity -> {
            RestaurantDto restaurantDto = restaurantMapper.mapTo(restaurantEntity);
            restaurantDto.setImage(restaurantEntity.getImage());
            return new ResponseEntity<>(restaurantDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(path = "")
    public ResponseEntity<RestaurantDto> createRestaurant(@RequestBody RestaurantDto restaurantDto) throws IOException {
        RestaurantEntity restaurantEntity = restaurantMapper.mapFrom(restaurantDto);
        RestaurantEntity savedRestaurantEntity = restaurantService.save(restaurantEntity);
        RestaurantDto savedRestaurantDto = restaurantMapper.mapTo(savedRestaurantEntity);
        return new ResponseEntity<>(savedRestaurantDto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_RESTAURANT')")
    @PatchMapping(path = "/{id}")
    public ResponseEntity<RestaurantDto> updateRestaurant(@PathVariable("id") Long id,
                                                          @RequestBody RestaurantDto restaurantDto
    ) throws IOException {
        if (!restaurantService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        RestaurantEntity restaurantEntity = restaurantMapper.mapFrom(restaurantDto);
        restaurantEntity.setRestaurantId(id);
        RestaurantEntity savedRestaurantEntity = restaurantService.save(restaurantEntity, id);
        RestaurantDto savedRestaurantDto = restaurantMapper.mapTo(savedRestaurantEntity);
        return new ResponseEntity<>(savedRestaurantDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<RestaurantDto> deleteRestaurant(@PathVariable("id") Long id) {
        if (!restaurantService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        restaurantService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //    @PreAuthorize("hasRole('ROLE_RESTAURANT')")
    @GetMapping(path = "/user/{id}")
    public ResponseEntity<RestaurantDto> getRestUser(@PathVariable("id") Long id) {
        Optional<UserEntity> optionalUserEntity = userService.findOne(id);
        UserEntity userEntity = optionalUserEntity.orElseThrow(() -> new IllegalArgumentException("Could not find the user"));

        Optional<RestaurantEntity> optionalRestaurantEntity = restaurantService.findByEmail(userEntity.getEmail());
        RestaurantEntity restaurant = optionalRestaurantEntity.orElseThrow(() -> new IllegalArgumentException("Could not find the user"));

        RestaurantDto restaurantDto = restaurantMapper.mapTo(restaurant);
        return new ResponseEntity<>(restaurantDto, HttpStatus.OK);
    }
}
