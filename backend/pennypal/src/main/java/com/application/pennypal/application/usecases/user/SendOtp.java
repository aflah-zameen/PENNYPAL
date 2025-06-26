package com.application.pennypal.application.usecases.user;

import java.time.Instant;
import java.time.LocalDateTime;

public interface SendOtp {
    LocalDateTime send(String email);
    LocalDateTime resend(String email);
}
