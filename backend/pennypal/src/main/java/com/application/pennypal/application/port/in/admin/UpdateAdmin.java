package com.application.pennypal.application.port.in.admin;

import com.application.pennypal.domain.valueObject.UserDomainDTO;
import org.springframework.web.multipart.MultipartFile;

public interface UpdateAdmin {
    UserDomainDTO execute(String adminId, String name, String phone, MultipartFile file);
}
