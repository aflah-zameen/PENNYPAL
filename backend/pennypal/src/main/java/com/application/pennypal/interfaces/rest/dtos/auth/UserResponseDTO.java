package com.application.pennypal.interfaces.rest.dtos.auth;

import com.application.pennypal.domain.user.valueObject.Roles;

import java.time.LocalDateTime;
import java.util.Set;

public record UserResponseDTO(String id,
                              String name,
                              String email,
                              Set<Roles> roles,
                              String phone,
                              boolean active,
                              boolean verified,
                              LocalDateTime created,
                              String profileURL,
                              String accessToken) {
}
