package com.application.pennypal.domain.exception.transaction;

import com.application.pennypal.domain.shared.exception.DomainErrorCode;
import com.application.pennypal.domain.shared.exception.base.DomainValidationException;

public class MissingPaymentMethodExceptionDomain extends DomainValidationException {
    public MissingPaymentMethodExceptionDomain() {
        super("Payment method is required", DomainErrorCode.MISSING_PAYMENT_METHOD);
    }
}
