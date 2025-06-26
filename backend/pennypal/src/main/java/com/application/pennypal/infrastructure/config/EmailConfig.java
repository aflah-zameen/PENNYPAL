package com.application.pennypal.infrastructure.config;

import com.application.pennypal.application.port.OtpRepositoryPort;
import com.application.pennypal.application.port.OtpServicePort;
import com.application.pennypal.application.port.UserRepositoryPort;
import com.application.pennypal.application.service.SendOtpService;
import com.application.pennypal.application.usecases.user.SendOtp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {
    @Bean
    public SendOtp sendOtp(UserRepositoryPort userRepository, OtpServicePort otpServicePort){
        return new SendOtpService(userRepository,otpServicePort);
    }
}
