package com.application.pennypal.application.exception;

public enum ApplicationErrorCode {

    // Validation
    MISSING_INPUT_DATA("VAL_APP_001"),
    INVALID_TRANSFER_TO_SELF("VAL_APP_002"),

    // Business Rule
    INSUFFICIENT_BALANCE("BUS_APP_001"),

    // Not Found
    USER_NOT_FOUND("APP_NOT_FOUND_001"),
    TRANSACTION_NOT_FOUND("APP_NOT_FOUND_002"),

    // Notification
    NOTIFICATION_SEND_FAILED("NOTF_APP_001"),

    // System
    SERVICE_COMMUNICATION_FAILURE("SYS_APP_001"),
    UNKNOWN_INTERNAL_ERROR("SYS_APP_999");

    private final String code;

    ApplicationErrorCode(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }
}
