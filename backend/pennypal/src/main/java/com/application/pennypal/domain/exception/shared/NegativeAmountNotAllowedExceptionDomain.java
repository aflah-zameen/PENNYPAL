package com.application.pennypal.domain.exception.shared;

import com.application.pennypal.domain.exception.base.DomainBusinessException;

public class NegativeAmountNotAllowedExceptionDomain extends DomainBusinessException {
    public NegativeAmountNotAllowedExceptionDomain(){
        super("Amount must be positive");
    }
}
