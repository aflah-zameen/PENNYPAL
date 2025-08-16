package com.application.pennypal.domain.user.valueObject;

public enum Roles {
    USER("USER"),
    ADMIN("ADMIN"),
    SUPER_ADMIN("SUPER_ADMIN"),
    GUEST("GUEST");
    private final String value;
    Roles(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
