package com.application.pennypal.shared.exception;

public class OtpInvalidException extends ApplicationException {
    public OtpInvalidException(String message) {
        super(message,"INVALID_OTP");
    }
}
