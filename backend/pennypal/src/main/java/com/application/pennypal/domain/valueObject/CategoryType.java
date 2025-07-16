package com.application.pennypal.domain.valueObject;

import lombok.Getter;

@Getter
public enum CategoryType {
    GOAL("GOAL"),
    INCOME("INCOME"),
    EXPENSE("EXPENSE"),
    SHARED("SHARED");
        private  final String value;
        CategoryType(String value){
            this.value = value;
        }
}
