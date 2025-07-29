package com.rajan.foodDeliveryApp.services.authentication;


import com.rajan.foodDeliveryApp.domain.Role;
import com.rajan.foodDeliveryApp.domain.dto.AuthenticationResponse;
import com.rajan.foodDeliveryApp.domain.dto.LoginRequest;
import com.rajan.foodDeliveryApp.domain.dto.RegisterRequest;
import com.rajan.foodDeliveryApp.domain.dto.UserDto;
import com.rajan.foodDeliveryApp.domain.entities.UserEntity;
import com.rajan.foodDeliveryApp.mappers.Mapper;
import com.rajan.foodDeliveryApp.repositories.UserRepository;
import com.rajan.foodDeliveryApp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final UserRepository userRepository;
    private final UserService userService;
    private final Mapper<UserEntity, UserDto> userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public ResponseEntity<AuthenticationResponse> register(RegisterRequest registerRequest) {
        if (userService.isExistsByEmail(registerRequest.getEmail())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT) // HTTP 409 Conflict
                    .body(new AuthenticationResponse("User account with this email already exists."));
        }
        UserEntity u = UserEntity.builder()
                .email(registerRequest.getEmail())
                .password(bCryptPasswordEncoder.encode(registerRequest.getPassword()))
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .role(registerRequest.getRole() != null? registerRequest.getRole() : Role.USER)
                .contact(registerRequest.getContact())
                .build();
        userRepository.save(u);
        UserDto userDto = userMapper.mapTo(u);
        String token = jwtService.generateToken(u);
        Date issuedAt = jwtService.getIssuedDate(token);
        Date expirationDate = jwtService.getExpirationDate(token);
        AuthenticationResponse response = AuthenticationResponse.builder()
                .user(userDto)
                .token(token)
                .issuedDate(issuedAt)
                .expirationDate(expirationDate)
                .build();

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<AuthenticationResponse> login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
            /* the authentication manager intercepts the response in-case of invalid credentials,
              so I opted for a try-catch block
             */
        } catch (AuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthenticationResponse("Invalid credentials"));
        }
        Optional<UserEntity> userOptional = userRepository.findByEmail(loginRequest.getEmail());

        if (userOptional.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new AuthenticationResponse("User not found"));
        }
        UserEntity user = userOptional.get();
        UserDto userDto = userMapper.mapTo(user);
        String token = jwtService.generateToken(user);
        Date issuedAt = jwtService.getIssuedDate(token);
        Date expirationDate = jwtService.getExpirationDate(token);

        AuthenticationResponse response = AuthenticationResponse.builder()
                .user(userDto)
                .token(token)
                .issuedDate(issuedAt)
                .expirationDate(expirationDate)
                .build();

        return ResponseEntity.ok(response);
    }
}
