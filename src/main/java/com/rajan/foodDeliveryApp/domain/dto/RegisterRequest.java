package com.rajan.foodDeliveryApp.domain.dto;

import com.rajan.foodDeliveryApp.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RegisterRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String contact;
    private Role role;
}