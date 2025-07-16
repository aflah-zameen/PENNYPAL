package com.application.pennypal.interfaces.rest.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ResendOtpRequest {
    @NotBlank(message = "Email cannot be null")
    @Email(message = "Email format is not correct")
    private String email;
}
