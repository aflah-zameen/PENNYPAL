package com.application.pennypal.domain.user.validator;

import com.application.pennypal.domain.user.exception.validation.InvalidPasswordDomainException;
import com.application.pennypal.domain.user.exception.validation.MissingPasswordDomainException;
import java.util.regex.Pattern;

public class EncodedPasswordValidator {
    private final static String PASSWORD_EXPR = "^\\$2[ayb]\\$.{56}$";
    public static void validate(String encodePassword){
        if(encodePassword == null || encodePassword.trim().isEmpty() ){
            throw new MissingPasswordDomainException("Encoded Password must not be null");

        }
        if(!Pattern.matches(PASSWORD_EXPR,encodePassword)){
            throw new InvalidPasswordDomainException("Encoded Password format is wrong.");
        }
    }
}
