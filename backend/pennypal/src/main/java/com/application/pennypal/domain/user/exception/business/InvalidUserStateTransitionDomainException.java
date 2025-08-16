package com.application.pennypal.domain.user.exception.business;

import com.application.pennypal.domain.shared.exception.DomainErrorCode;
import com.application.pennypal.domain.shared.exception.base.DomainBusinessException;

public class InvalidUserStateTransitionDomainException extends DomainBusinessException{
    public InvalidUserStateTransitionDomainException(String message,DomainErrorCode code) {
        super(message, code);
    }
}
