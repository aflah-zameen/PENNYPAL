package com.application.pennypal.application.dto;

import org.springframework.web.multipart.MultipartFile;

public record UserUpdateApplicationDTO(String name, String email, String phone, MultipartFile file) {
};
