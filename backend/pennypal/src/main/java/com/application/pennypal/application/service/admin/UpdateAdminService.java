package com.application.pennypal.application.service.admin;

import com.application.pennypal.application.exception.usecase.user.UserNotFoundApplicationException;
import com.application.pennypal.application.mappers.user.UserApplicationMapper;
import com.application.pennypal.application.port.in.admin.UpdateAdmin;
import com.application.pennypal.application.port.out.repository.UserRepositoryPort;
import com.application.pennypal.application.port.out.service.FileUploadPort;
import com.application.pennypal.domain.user.entity.User;
import com.application.pennypal.domain.valueObject.UserDomainDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
public class UpdateAdminService implements UpdateAdmin {
    private final UserRepositoryPort userRepositoryPort;
    private final FileUploadPort fileUploadPort;
    @Override
    public UserDomainDTO execute(String adminId, String name, String phone, MultipartFile file) {
        User user = userRepositoryPort.findByUserId(adminId)
                .orElseThrow(() -> new UserNotFoundApplicationException("User not found"));
        String url = null;
        if(file != null){
            url = fileUploadPort.uploadFile(file);
            user = user.changeProfileURL(url);
        }
        if(name != null)
            user = user.changeName(name);
        if(phone != null)
         user =user.changePhone(phone);

        user = userRepositoryPort.update(user);
       return new UserDomainDTO(
               user.getUserId(),
               user.getName(),
               user.getEmail(),
               user.getRoles(),
               user.getPhone(),
               user.isActive(),
               user.isVerified(),
               user.getCreatedAt(),
               user.getProfileURL().orElse(null)
       );
    }
}
