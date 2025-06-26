package com.application.pennypal.application.dto;

import com.application.pennypal.domain.user.valueObject.Roles;

import java.time.LocalDateTime;

public record UserFiltersDTO(Roles role, LocalDateTime joinedBefore,
                             LocalDateTime joinedAfter,Boolean status
                             ) {
}
