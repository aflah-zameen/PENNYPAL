package com.application.pennypal.interfaces.rest.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ResetPasswordRequestDTO(@NotNull(message = "Email cannot be null")
                                      @NotBlank(message = "Email cannot be blank")
                                      @Email(message = "Invalid email format")
                                      String email,
                                      @NotNull(message = "Password is required")
                                      @NotBlank(message = "Password is required")
                                      String password,
                                      @NotNull(message = "verification token is required")
                                      @NotBlank(message = "verification token is required")
                                      String verificationToken) {
}
