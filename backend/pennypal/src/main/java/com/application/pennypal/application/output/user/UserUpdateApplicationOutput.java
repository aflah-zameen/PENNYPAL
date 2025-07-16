package com.application.pennypal.application.output.user;

import org.springframework.web.multipart.MultipartFile;

public record UserUpdateApplicationOutput(String name, String email, String phone, MultipartFile file) {
};
