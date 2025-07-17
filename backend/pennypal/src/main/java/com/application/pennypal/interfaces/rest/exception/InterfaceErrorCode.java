package com.application.pennypal.interfaces.rest.exception;

public enum InterfaceErrorCode {

    // ==== Validation Errors ====
    MISSING_REQUIRED_FIELD("INTF_VAL_001"),
    INVALID_REQUEST_FORMAT("INTF_VAL_002"),
    UNSUPPORTED_MEDIA_TYPE("INTF_VAL_003"),

    // ==== Authentication / Authorization Errors ====
    UNAUTHORIZED("INTF_AUTH_001"),
    FORBIDDEN("INTF_AUTH_002"),

    // ==== Resource Errors ====
    RESOURCE_NOT_FOUND("INTF_RES_001"),
    RESOURCE_ALREADY_EXISTS("INTF_RES_002"),

    // ==== System Errors ====
    INTERNAL_SERVER_ERROR("INTF_SYS_001"),
    EXTERNAL_SERVICE_ERROR("INTF_SYS_002"),

    // ==== Fallback / Unexpected ====
    UNEXPECTED_ERROR("INTF_GEN_001");

    private final String value;

    InterfaceErrorCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
