package com.application.pennypal.application.output.user;

import com.application.pennypal.domain.valueObject.Roles;

import java.time.LocalDateTime;

public record UserFiltersOutput(Roles role, LocalDateTime joinedBefore,
                                LocalDateTime joinedAfter, Boolean status
                             ) {
}
