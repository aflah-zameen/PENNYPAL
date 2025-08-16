package com.application.pennypal.domain.user.exception.business;

import com.application.pennypal.domain.shared.exception.DomainErrorCode;
import com.application.pennypal.domain.shared.exception.base.DomainBusinessException;

public class MinimumUserRoleRequiredDomainException extends DomainBusinessException {
    public MinimumUserRoleRequiredDomainException(String message) {
        super(message, DomainErrorCode.USER_MUST_HAVE_AT_LEAST_ONE_ROLE);
    }
}
