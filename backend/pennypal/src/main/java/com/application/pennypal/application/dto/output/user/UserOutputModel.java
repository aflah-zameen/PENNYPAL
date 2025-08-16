package com.application.pennypal.application.dto.output.user;

import com.application.pennypal.domain.user.valueObject.Roles;

import java.time.LocalDateTime;
import java.util.Set;

public record UserOutputModel(String id, String userName, String email, Set<Roles> role, String profileURL, Boolean active, Boolean verified,
                              LocalDateTime createdAt,LocalDateTime updatedAt) {
}
