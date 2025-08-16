package com.application.pennypal.domain.valueObject;

import java.time.Instant;

public record RefreshTokenInfo(String userId,String token, Instant expiryDate,String ipAddress) {
}
