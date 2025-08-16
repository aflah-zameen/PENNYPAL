package com.application.pennypal.infrastructure.config.beans.usecase;

import com.application.pennypal.application.port.out.repository.UserRepositoryPort;
import com.application.pennypal.application.port.out.service.EmailSendPort;
import com.application.pennypal.application.port.out.service.OtpCachePort;
import com.application.pennypal.application.port.out.service.OtpGeneratorPort;
import com.application.pennypal.application.service.auth.ReSendOtpService;
import com.application.pennypal.application.port.in.user.ReSendOtp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {
    @Bean
    public ReSendOtp sendOtp(OtpCachePort otpCachePort, EmailSendPort emailSendPort,
                             UserRepositoryPort userRepositoryPort, OtpGeneratorPort otpGeneratorPort){
        return new ReSendOtpService(otpCachePort,emailSendPort,userRepositoryPort,otpGeneratorPort);
    }
}
