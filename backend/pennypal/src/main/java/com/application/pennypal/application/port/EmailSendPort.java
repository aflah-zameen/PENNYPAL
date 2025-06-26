package com.application.pennypal.application.port;

public interface EmailSendPort {
    void sendEmail(String toEmail,String FromEmail, String body,String subject);
}
