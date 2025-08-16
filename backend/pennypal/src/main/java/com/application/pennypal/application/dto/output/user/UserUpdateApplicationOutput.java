package com.application.pennypal.application.dto.output.user;

import org.springframework.web.multipart.MultipartFile;

public record UserUpdateApplicationOutput(String name, String email, String phone, MultipartFile file) {
};
