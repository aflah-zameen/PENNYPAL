package com.application.pennypal.infrastructure.exception;

public enum InfraErrorCode {
    DATABASE_ERROR("INFRA_DB_001"),
    REST_API_FAILURE("INFRA_EXT_001"),
    FILE_STORAGE_FAILURE("INFRA_FS_001"),
    MESSAGE_QUEUE_FAILURE("INFRA_MSG_001");

    private final String code;

    InfraErrorCode(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }
}

