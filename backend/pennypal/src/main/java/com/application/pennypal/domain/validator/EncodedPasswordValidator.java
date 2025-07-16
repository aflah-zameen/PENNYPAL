package com.application.pennypal.domain.validator;

import com.application.pennypal.shared.exception.InvalidPasswordArgumentException;

import java.util.regex.Pattern;

public class EncodedPasswordValidator {
    private final static String PASSWORD_EXPR = "^\\$2[ayb]\\$.{56}$";
    public static void validate(String encodePassword){
        if(encodePassword == null || encodePassword.trim().isEmpty() || !Pattern.matches(PASSWORD_EXPR,encodePassword)){
            throw new InvalidPasswordArgumentException("Password is null or not in proper format.");
        }
    }
}
