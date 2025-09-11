package com.application.pennypal.interfaces.rest.dtos.auth;

import java.time.Instant;

public record RegisterResponseDTO(
        String id,
        String email,
        Instant expiry
) {
}
