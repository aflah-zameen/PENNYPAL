package com.application.pennypal.infrastructure.event.listener;

import com.application.pennypal.application.port.out.service.EmailSendPort;
import com.application.pennypal.application.port.out.service.VerificationTokenServicePort;
import com.application.pennypal.domain.user.event.AdminRegisteredEvent;
import com.application.pennypal.infrastructure.exception.email.EmailSendFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminRegisteredEventListener {
    private final VerificationTokenServicePort verificationTokenServicePort;
    private final EmailSendPort emailSendPort;

    @Async
    @EventListener
    public void handle(AdminRegisteredEvent adminRegisteredEvent){
        try{
            String verificationToken = verificationTokenServicePort.getToken(adminRegisteredEvent.getEmail());
            verificationTokenServicePort.saveToken(verificationToken,adminRegisteredEvent.getEmail());
            emailSendPort.sendAdminVerification(adminRegisteredEvent.getName(),
                    adminRegisteredEvent.getEmail(),verificationToken);
        }catch(Exception ex){
            throw new EmailSendFailedException("Error in sending email");
        }
    }
}
