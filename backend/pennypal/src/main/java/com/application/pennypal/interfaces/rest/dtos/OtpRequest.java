package com.application.pennypal.interfaces.rest.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.Optional;

@Getter
public class OtpRequest {
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "OTP is required")
    @Pattern(regexp = "\\d{6}",message = "OTP must be 6 digit number")
    private String otp;

    @NotBlank(message = "Context is required")
    private String context;

}
