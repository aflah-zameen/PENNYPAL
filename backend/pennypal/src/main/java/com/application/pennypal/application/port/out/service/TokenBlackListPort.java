package com.application.pennypal.application.port.out.service;

import java.time.Duration;

public interface TokenBlackListPort {
    void blacklist(String token, Duration expiry);
    boolean isBlacklisted(String token);
}
