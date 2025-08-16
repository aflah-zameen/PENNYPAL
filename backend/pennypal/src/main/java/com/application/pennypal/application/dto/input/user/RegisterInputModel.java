package com.application.pennypal.application.dto.input.user;

import org.springframework.web.multipart.MultipartFile;

public record RegisterInputModel(String userName, String email, String password, String phone,
                                 MultipartFile userProfile) {
}
