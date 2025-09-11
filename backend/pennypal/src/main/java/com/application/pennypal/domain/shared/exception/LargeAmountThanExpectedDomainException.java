package com.application.pennypal.domain.shared.exception;

import com.application.pennypal.domain.shared.exception.base.DomainBusinessException;

public class LargeAmountThanExpectedDomainException extends DomainBusinessException {
    public LargeAmountThanExpectedDomainException(String message) {
        super(message,DomainErrorCode.INVALID_AMOUNT);
    }
}
