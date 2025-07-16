package com.application.pennypal.interfaces.rest.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class AdminRegisterRequest {
    @NotBlank(message = "Email cannot be null")
    @Email(message = "Email has not proper format")
    private String email;

    @NotBlank(message = "Password cannot be null")
    private String password;

    @NotBlank(message = "Name cannot be null")
    private String name;

    @NotBlank(message = "Role cannot be null")
    private String role;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "\\d{10}", message = "Phone must be a 10-digit number")
    private String phone;
}
