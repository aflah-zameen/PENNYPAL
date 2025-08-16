package com.application.pennypal.application.dto.output.user;

import java.time.LocalDateTime;

public record RegisterOutputModel(
        String id,
        String email,
        LocalDateTime expiry
) {
}
