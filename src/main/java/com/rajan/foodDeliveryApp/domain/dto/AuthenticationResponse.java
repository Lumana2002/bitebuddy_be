package com.rajan.foodDeliveryApp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private UserDto user;
    private String token;
    private Date issuedDate;
    private Date expirationDate;
    private String errorMessage;


    public AuthenticationResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
