package com.application.pennypal.shared.exception;

public class IpAddressMismatchException extends ApplicationException {
    public IpAddressMismatchException(String message) {
        super(message,"IP_ADDRESS_MISMATCH");
    }
}
