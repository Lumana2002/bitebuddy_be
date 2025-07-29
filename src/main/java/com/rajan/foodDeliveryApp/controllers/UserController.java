package com.rajan.foodDeliveryApp.controllers;

import com.rajan.foodDeliveryApp.domain.dto.UserDto;
import com.rajan.foodDeliveryApp.domain.entities.UserEntity;
import com.rajan.foodDeliveryApp.mappers.Mapper;
import com.rajan.foodDeliveryApp.mappers.impl.UserMapperImpl;
import com.rajan.foodDeliveryApp.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final Mapper<UserEntity, UserDto> userMapper;

    @Autowired
    public UserController(UserService userService, UserMapperImpl userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }


    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") Long id) {
        if (!userService.isExists(id)) {
            return ResponseEntity.notFound().build();
        }
        Optional<UserEntity> userEntity = userService.findOne(id);
        return userEntity.map(user -> {
            UserDto userDto = userMapper.mapTo(user);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    public Page<UserDto> listUsers(Pageable pageable) {
        Page<UserEntity> usersList = userService.findAll(pageable);
        return usersList.map(userMapper::mapTo);
    }
//    Validate the user's identity for updating
    @PatchMapping("/users/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long id, @RequestBody UserDto userDto) {
        if (!userService.isExists(id)) {
            return ResponseEntity.notFound().build();
        }
        UserEntity userEntity = userMapper.mapFrom(userDto);
        UserEntity updatedUser = userService.save(userEntity, id);
        return ResponseEntity.ok(userMapper.mapTo(updatedUser));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable("id") Long id) {
        if (!userService.isExists(id)) {
            return ResponseEntity.notFound().build();
        }
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}