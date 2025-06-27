package com.application.pennypal.application.usecases.user;

import com.application.pennypal.domain.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface CreateUser {
    User execute (String name, String email, String password, String phone, String role, MultipartFile profileImageFile);
}
