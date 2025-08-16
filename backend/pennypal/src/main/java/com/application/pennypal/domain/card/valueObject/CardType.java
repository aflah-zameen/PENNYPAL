package com.application.pennypal.domain.card.valueObject;

public enum CardType {
    DEBIT("DEBIT"),
    CREDIT("CREDIT"),
    VISA("VISA"),
    MASTERCARD("MASTERCARD");
    private final String value;
    CardType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
