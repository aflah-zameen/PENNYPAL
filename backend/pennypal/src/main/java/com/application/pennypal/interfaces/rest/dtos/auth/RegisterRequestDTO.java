package com.application.pennypal.interfaces.rest.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class RegisterRequestDTO {
    @NotBlank(message = "Name is required ")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "\\d{10}", message = "Phone must be a 10-digit number")
    private String phone;

    @NotNull(message = "User profile picture is required")
    private MultipartFile profilePicture;

}
