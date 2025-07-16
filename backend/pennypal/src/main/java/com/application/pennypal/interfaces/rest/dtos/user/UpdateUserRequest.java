package com.application.pennypal.interfaces.rest.dtos.user;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public record UpdateUserRequest (String name, @NotBlank(message = "Email cannot be null") String email, String phone, MultipartFile profilePicture
                                 ){}
