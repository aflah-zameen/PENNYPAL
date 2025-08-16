package com.application.pennypal.infrastructure.exception;

public enum InfraErrorCode {
    DATABASE_ERROR("INFRA_DB_001"),
    REST_API_FAILURE("INFRA_EXT_001"),
    FILE_STORAGE_FAILURE("INFRA_FS_001"),
    MESSAGE_QUEUE_FAILURE("INFRA_MSG_001"),
    EMAIL_FAILURE("INFRA_EML_001"),
    EMAIL_SEND_FAILURE("INFRA_EML_002"),
    TOKEN_HASHING_INVALIDATION("INFRA_HAS_001"),

    /// Auth error code
    INVALID_ACCESS_TOKEN("INFRA_AUT_001"),
    TOKEN_BLACKLISTED("INFRA_AUT_002");

    private final String code;

    InfraErrorCode(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }
}

