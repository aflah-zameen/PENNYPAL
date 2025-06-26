package com.application.pennypal.application.dto;

import org.springframework.cglib.core.Local;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record OtpEntityDTO(Long id, String email, String otp, LocalDateTime createdAt,
                           LocalDateTime expiresAt, boolean used){}
