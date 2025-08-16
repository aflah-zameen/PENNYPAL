package com.application.pennypal.domain.user.validator;

import com.application.pennypal.domain.user.exception.validation.MissingRoleDomainException;
import com.application.pennypal.domain.user.valueObject.Roles;

import java.util.Set;

public class RoleValidator {
    public static void validate(Set<Roles> roles) throws IllegalArgumentException{
        if(roles==null ||roles.isEmpty()){
            throw new MissingRoleDomainException("Roles cannot be null");
        }
    }
}
