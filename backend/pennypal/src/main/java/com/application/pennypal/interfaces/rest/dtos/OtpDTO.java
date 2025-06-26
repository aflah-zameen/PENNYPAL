package com.application.pennypal.interfaces.rest.dtos;

import java.time.LocalDateTime;

public record OtpDTO(LocalDateTime expiresAt) {
}
