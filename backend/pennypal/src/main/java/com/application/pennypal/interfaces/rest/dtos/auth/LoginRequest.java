package com.application.pennypal.interfaces.rest.dtos.auth;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequest {
    @NotBlank(message = "Email cannot be null")
    @Email(message = "Email is not in proper format")
    private String email;
    @NotBlank(message = "Password is empty")
    private String password;
}
