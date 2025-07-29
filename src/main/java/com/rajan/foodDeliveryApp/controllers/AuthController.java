package com.rajan.foodDeliveryApp.controllers;

import com.rajan.foodDeliveryApp.domain.dto.AuthenticationResponse;
import com.rajan.foodDeliveryApp.domain.dto.LoginRequest;
import com.rajan.foodDeliveryApp.domain.dto.RegisterRequest;
import com.rajan.foodDeliveryApp.services.authentication.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping(path = "/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
}