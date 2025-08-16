package com.application.pennypal.domain.user.validator;

import com.application.pennypal.domain.user.exception.validation.InvalidPasswordDomainException;
import com.application.pennypal.domain.user.exception.validation.MissingPasswordDomainException;

public class PasswordValidator {

    public static void validate(String password){
        if(password == null){
            throw new MissingPasswordDomainException("Password cannot be null");
        }
        if(password.length() < 8){
            throw new InvalidPasswordDomainException("Password must be at least 8 characters long");
        }
        if(!password.matches(".*[a-z].*")) {
            throw new InvalidPasswordDomainException("Password must contain at least one lowercase letter");
        }
        if(!password.matches(".*[A-Z].*")){
            throw new InvalidPasswordDomainException("Password must contain at least one uppercase letter");
        }
        if (!password.matches(".*\\d.*")) {
            throw new InvalidPasswordDomainException("Password must contain at least one number");
        }
        if (!password.matches(".*[@#$%^&*()!].*")) {
            throw new InvalidPasswordDomainException("Password must contain at least one special character");
        }
    }
}
