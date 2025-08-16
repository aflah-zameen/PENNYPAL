package com.application.pennypal.application.mappers.user;

import com.application.pennypal.application.dto.output.user.UserOutputModel;
import com.application.pennypal.domain.user.entity.User;

public class UserApplicationMapper {
    public static UserOutputModel toOutput(User user){
        return new UserOutputModel(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getRoles(),
                user.getProfileURL().isPresent() ? user.getProfileURL().get() : null,
                user.isActive(),
                user.isVerified(),
                user.getCreatedAt(),
                user.getUpdatedAt().isPresent() ? user.getUpdatedAt().get() : null
        );
    }
}
