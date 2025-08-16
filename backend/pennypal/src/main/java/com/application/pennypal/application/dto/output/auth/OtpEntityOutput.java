package com.application.pennypal.application.dto.output.auth;

import java.time.LocalDateTime;

public record OtpEntityOutput(Long id, String email, String otp, LocalDateTime createdAt,
                              LocalDateTime expiresAt, boolean used){}
