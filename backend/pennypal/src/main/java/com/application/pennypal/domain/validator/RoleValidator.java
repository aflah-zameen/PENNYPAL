package com.application.pennypal.domain.validator;

import com.application.pennypal.shared.exception.InvalidRoleException;
import com.application.pennypal.domain.valueObject.Roles;

import java.util.Set;

public class RoleValidator {
    public static void validate(Set<Roles> roles) throws IllegalArgumentException{
        if(roles==null ||roles.isEmpty()){
            throw new InvalidRoleException("Roles cannot be null");
        }
    }
}
