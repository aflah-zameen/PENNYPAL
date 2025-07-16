package com.application.pennypal.interfaces.rest.dtos.auth;

import java.time.LocalDateTime;

public record OtpDTO(LocalDateTime expiresAt) {
}
