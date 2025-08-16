package com.application.pennypal.infrastructure.persistence.jpa.mapper;

import com.application.pennypal.domain.user.entity.User;
import com.application.pennypal.infrastructure.persistence.jpa.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserJpaMapper {

    public static User toDomain(UserEntity userEntity){
        return User.reconstruct(
                userEntity.getUserId(),
                userEntity.getName(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getPhone(),
                userEntity.getRoles(),
                userEntity.isActive(),
                userEntity.isVerified(),
                userEntity.getCreatedAt(),
                userEntity.getUpdatedAt(),
                userEntity.getProfileURL()
        );
    }

    public static UserEntity toEntity(User user){
        return new UserEntity(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getPhone(),
                user.getRoles(),
                user.isActive(),
                user.isVerified(),
                user.getProfileURL().isPresent()?user.getProfileURL().get() : null
        );
    }


}
