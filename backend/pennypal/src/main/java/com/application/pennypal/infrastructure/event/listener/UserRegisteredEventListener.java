package com.application.pennypal.infrastructure.event.listener;

import com.application.pennypal.application.port.out.service.EmailSendPort;
import com.application.pennypal.application.port.out.service.OtpCachePort;
import com.application.pennypal.application.port.out.service.OtpGeneratorPort;
import com.application.pennypal.domain.user.event.UserRegisteredEvent;
import com.application.pennypal.infrastructure.config.properties.AuthProperties;
import com.application.pennypal.infrastructure.exception.email.EmailSendFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRegisteredEventListener {
    private final OtpCachePort otpCachePort;
    private final EmailSendPort emailSendPort;
    private final OtpGeneratorPort otpGeneratorPort;

    @Async
    @EventListener
    public void handle(UserRegisteredEvent event){
        try{
            String generatedOtp = otpGeneratorPort.generate();
            otpCachePort.saveOtp(event.getEmail(),generatedOtp);
            emailSendPort.sendUserVerificationOtp(event.getEmail(),event.getName(), generatedOtp);
        }
        catch (Exception ex){
            throw new EmailSendFailedException("Failed to process UserRegisteredEvent");
        }
    }
}
