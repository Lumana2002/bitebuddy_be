package com.rajan.foodDeliveryApp.controllers;

import com.rajan.foodDeliveryApp.domain.dto.GenericResponse;
import com.rajan.foodDeliveryApp.domain.dto.PasswordResetDto;
import com.rajan.foodDeliveryApp.domain.entities.UserEntity;
import com.rajan.foodDeliveryApp.services.UserService;
import com.rajan.foodDeliveryApp.services.mail.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/password-reset")
public class PasswordResetController {

    private static final Logger log = LoggerFactory.getLogger(PasswordResetController.class);
    private final UserService userService;
    private final EmailService emailService;

    public PasswordResetController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @PostMapping("/request")
    public ResponseEntity<GenericResponse> resetPassword(HttpServletRequest request, @RequestParam("email") String userEmail) {
        Optional<UserEntity> userOptional = userService.findByEmail(userEmail);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new GenericResponse("User not found"));
        }

        UserEntity user = userOptional.get();

        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);

        String resetUrl = "http://localhost:3000/reset-password/?token=" + token;

        emailService.sendResetTokenEmail(user.getEmail(), resetUrl);

        return ResponseEntity.ok(new GenericResponse("Reset password email sent to " + userEmail));
    }

    @PostMapping("/change-password")
    public ResponseEntity<GenericResponse> changePassword(@RequestBody PasswordResetDto passwordResetDTO) {
        log.info(String.valueOf(passwordResetDTO));
        boolean isValid = userService.validatePasswordResetToken(passwordResetDTO.getToken());

        if (!isValid) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new GenericResponse("Invalid token"));
        }

        UserEntity user = userService.findByPasswordResetToken(passwordResetDTO.getToken())
                .orElseThrow(() -> new RuntimeException("User not found"));
        userService.updatePassword(user, passwordResetDTO.getNewPassword(),passwordResetDTO.getToken());
        return ResponseEntity.ok(new GenericResponse("Password successfully reset."));
    }
}
