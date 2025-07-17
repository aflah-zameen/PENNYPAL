package com.application.pennypal.domain.exception;

public enum DomainErrorCode {

    // Validation - Transaction
    MISSING_TRANSACTION_ID("VAL_TRX_001"),
    INVALID_AMOUNT("VAL_TRX_002"),
    MISSING_TRANSACTION_DATE("VAL_TRX_003"),
    MISSING_TRANSACTION_TYPE("VAL_TRX_004"),

    // Validation - user
    MISSING_USER_ID("VAL_USR_001"),
    MISSING_USER_ID_TRANSACTION("VAL_TRX_005"),

    // Validation - Recurring
    MISSING_RECURRING_ID("VAL_RCG_001"),
    INVALID_RECURRING_TYPE("VAL_RCG_002"),

    //Validation - Category


    // Business Rule - Transaction
    INVALID_TRANSFER("BUS_TRX_001"),
    DUPLICATE_TRANSACTION("BUS_TRX_002"),

    // Business Rule - Recurring
    REQUIRED_RECURRING_ID_TRANSACTION("VAL_TRX_003"),
    INVALID_RECURRING_INTERVAL("BUS_RCG_002"),

    // Business Rule - Category
    REQUIRED_CATEG0RY_ID("BUS_CTG_001"),

    // Domain Rule - Transaction
    TRANSACTION_STATE_INVALID("DOM_TRX_001"),

    // Authorization
    USER_NOT_OWNER_OF_TRANSACTION("AUTH_TRX_001"),

    // System / Infra
    DATABASE_CONNECTION_FAILURE("SYS_DB_001");


    private final String value;

    DomainErrorCode(String value){
        this.value = value;
    }

    public String value(){
        return value;
    }
}
