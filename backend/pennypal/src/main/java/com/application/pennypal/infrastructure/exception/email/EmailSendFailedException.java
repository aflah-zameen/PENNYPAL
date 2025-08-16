package com.application.pennypal.infrastructure.exception.email;

import com.application.pennypal.infrastructure.exception.InfraErrorCode;
import com.application.pennypal.infrastructure.exception.base.EmailException;

public class EmailSendFailedException extends EmailException {
    public EmailSendFailedException(String message) {
        super(message, InfraErrorCode.EMAIL_SEND_FAILURE);
    }
}
