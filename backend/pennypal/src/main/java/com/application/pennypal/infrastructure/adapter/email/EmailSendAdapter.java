package com.application.pennypal.infrastructure.adapter.email;

import com.application.pennypal.application.port.EmailSendPort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSendAdapter implements EmailSendPort {
    final JavaMailSender javaMailSender;

    EmailSendAdapter(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmail(String toEmail, String FromEmail, String body, String subject) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(FromEmail);
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
    }
}
