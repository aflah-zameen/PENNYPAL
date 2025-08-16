package com.application.pennypal.domain.user.exception.business;

import com.application.pennypal.domain.shared.exception.DomainErrorCode;
import com.application.pennypal.domain.shared.exception.base.DomainBusinessException;

public class UserNotVerifiedForActivationDomainException extends DomainBusinessException {
    public UserNotVerifiedForActivationDomainException(String message) {
        super(message, DomainErrorCode.USER_NOT_VERIFIED_FOR_ACTIVATION);
    }
}
