package com.application.pennypal.domain.user.valueObject;

import java.time.Instant;
import java.util.Set;

public record UserDomainDTO(Long id, String name, String email, Set<Roles> roles, String phone, boolean active, boolean verified, Instant created, Instant updatedAt,String profileURL) {
}
