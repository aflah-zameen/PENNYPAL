package com.application.pennypal.application.dto.output.user;

import com.application.pennypal.domain.user.valueObject.Roles;

import java.time.LocalDateTime;

public record UserFiltersOutput(Roles role, LocalDateTime joinedBefore,
                                LocalDateTime joinedAfter, Boolean status
                             ) {
}
