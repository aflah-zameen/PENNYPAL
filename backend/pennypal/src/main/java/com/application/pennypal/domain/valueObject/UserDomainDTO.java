package com.application.pennypal.domain.valueObject;

import com.application.pennypal.domain.user.valueObject.Roles;

import java.time.LocalDateTime;
import java.util.Set;

public record UserDomainDTO(String userId, String name, String email, Set<Roles> roles, String phone, boolean active, boolean verified, LocalDateTime created, String profileURL) {
}
