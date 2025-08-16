package com.application.pennypal.domain.exception.transaction;

import com.application.pennypal.domain.shared.exception.DomainErrorCode;
import com.application.pennypal.domain.shared.exception.base.DomainBusinessException;

public class UnsupportedPaymentCombinationException extends DomainBusinessException {
    public UnsupportedPaymentCombinationException() {
        super("Unsupported payment configuration", DomainErrorCode.UNSUPPORTED_PAYMENT_COMBINATION);
    }
}
