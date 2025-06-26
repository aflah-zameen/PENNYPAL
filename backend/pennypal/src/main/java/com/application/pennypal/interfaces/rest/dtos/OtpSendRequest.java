package com.application.pennypal.interfaces.rest.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record OtpSendRequest(
        @NotBlank(message = "Email cannot be empty") @Email(message = "Invalid email format") String email,
        @NotBlank(message = "Context cannot be empty") String context) {
}
