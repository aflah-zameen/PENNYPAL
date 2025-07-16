package com.application.pennypal.application.port;

import com.application.pennypal.application.output.auth.OtpEntityOutput;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.OtpEntity;

import java.util.Optional;

public interface OtpRepositoryPort {
    Optional<OtpEntityOutput>findByOtp(String otp);
    OtpEntity save(OtpEntity otpEntity);
}
