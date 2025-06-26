package com.application.pennypal.infrastructure.adapter.persistence.jpa.mapper;

import com.application.pennypal.domain.user.entity.User;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.UserEntity;
import com.application.pennypal.interfaces.rest.dtos.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity toEntity(User user);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "roles", source = "roles")
    @Mapping(target = "active", source = "active")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    User toDomain(UserEntity userEntity);

    UserDto toDto(User user);
}
