package com.application.pennypal.application.exception;

public enum ApplicationErrorCode {


    // Validation
    MISSING_INPUT_DATA("VAL_APP_001"),
    INVALID_TRANSFER_TO_SELF("VAL_APP_002"),
    INVALID_ROLE_EXCEPTION("VAL_APP_003"),
    USER_SUSPENDED("VAL_APP_004"),

    // Business Rule
    INSUFFICIENT_BALANCE("BUS_APP_001"),
    DUPLICATE_EMAIL("BUS_APP_002"),
    INVALID_TOKEN_EXCEPTION("BUS_APP_003"),
    EMAIL_ALREADY_VERIFIED("BUS_APP_004"),
    INVALID_OTP_EXCEPTION("BUS_APP_005"),
    INVALID_CREDENTIALS("BUS_APP_006"),
    USER_INACTIVE("BUS_APP_007"),
    USER_NOT_VERIFIED("BUS_APP_008"),
    EMAIL_NOT_FOUND("BUS_APP_009"),
    CARD_NOT_FOUND("BUS_APP_010"),

    // Not Found
    USER_NOT_FOUND("APP_NOT_FOUND_001"),
    TRANSACTION_NOT_FOUND("APP_NOT_FOUND_002"),

    // Notification
    NOTIFICATION_SEND_FAILED("NOTF_APP_001"),

    // System
    SERVICE_COMMUNICATION_FAILURE("SYS_APP_001"),
    UNKNOWN_INTERNAL_ERROR("SYS_APP_999");

    private final String errorCode;

    ApplicationErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
