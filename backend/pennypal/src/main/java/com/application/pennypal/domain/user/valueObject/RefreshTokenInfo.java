package com.application.pennypal.domain.user.valueObject;

import java.time.Instant;

public record RefreshTokenInfo(Long userId,String token, Instant expiryDate,String ipAddress) {
}
