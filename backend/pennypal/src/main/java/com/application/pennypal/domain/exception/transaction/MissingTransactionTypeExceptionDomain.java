package com.application.pennypal.domain.exception.transaction;

import com.application.pennypal.domain.exception.base.DomainValidationException;

public class MissingTransactionTypeExceptionDomain extends DomainValidationException {
    public MissingTransactionTypeExceptionDomain(){
        super("Transaction type must not be null");
    }
}
