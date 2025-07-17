package com.application.pennypal.domain.exception.transaction;

import com.application.pennypal.domain.exception.base.DomainValidationException;

public class MissingTransactionDateExceptionDomain extends DomainValidationException {
    public MissingTransactionDateExceptionDomain(){
        super("Transaction date must not be null");
    }
}
