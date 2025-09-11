package com.application.pennypal.interfaces.rest.dtos.user;

import org.springframework.web.multipart.MultipartFile;

public record UpdateAdminRequest(
        String name,
        MultipartFile profile,
        String phone
) {
}
