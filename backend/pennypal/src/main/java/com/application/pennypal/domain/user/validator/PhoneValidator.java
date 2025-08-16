package com.application.pennypal.domain.user.validator;

import com.application.pennypal.domain.user.exception.validation.InvalidPhoneDomainException;
import com.application.pennypal.domain.user.exception.validation.MissingPhoneDomainException;

import java.util.regex.Pattern;

public class PhoneValidator {
    private static final String PHONE_PATTERN = "\\d{10}";
    public static void validate(String phone){
        if(phone == null || phone.trim().isBlank())
            throw new MissingPhoneDomainException("Phone cannot be null or empty");
        if(!Pattern.matches(PHONE_PATTERN,phone)){
            throw new InvalidPhoneDomainException("Phone number should be 10 digits");
        }
    }
}
