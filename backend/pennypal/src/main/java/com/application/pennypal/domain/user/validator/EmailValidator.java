package com.application.pennypal.domain.user.validator;

import com.application.pennypal.domain.user.exception.validation.InvalidEmailDomainValidation;
import com.application.pennypal.domain.user.exception.validation.MissingEmailDomainException;

import java.util.regex.Pattern;

public class EmailValidator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._%+-]{1,64}(?<!\\.)@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
    );

    public static void validate(String email){


        if(email == null || email.trim().isEmpty()){
            throw new MissingEmailDomainException("Email cannot be null");
        }
        if (email.length() > 254) {
            throw new InvalidEmailDomainValidation("Email exceeds maximum length of 254 characters");
        }

        int atCount = email.length() - email.replace("@","").length();
        if (atCount != 1) {
            throw new InvalidEmailDomainValidation("Email must contain exactly one @ symbol");
        }

        // Split into local part and domain
        String[] parts = email.split("@");
        String localPart = parts[0];
        String domainPart = parts[1];

        //validate local part
        if (localPart.isEmpty()) {
            throw new InvalidEmailDomainValidation("Local part cannot be empty");
        }
        if (localPart.length() > 64) {
            throw new InvalidEmailDomainValidation("Local part exceeds maximum length of 64 characters");
        }
        if (localPart.startsWith(".") || localPart.endsWith(".") || localPart.contains("..")) {
            throw new InvalidEmailDomainValidation("Local part cannot start/end with a dot or contain consecutive dots");
        }
        if (!localPart.matches("[a-zA-Z0-9._%+-]+")) {
            throw new InvalidEmailDomainValidation("Local part contains invalid characters");
        }

        //validate domain part
        if (domainPart.isEmpty()) {
            throw new InvalidEmailDomainValidation("Domain cannot be empty");
        }
        if (!domainPart.contains(".")) {
            throw new InvalidEmailDomainValidation("Domain must contain a top-level domain");
        }
        if (domainPart.startsWith("-") || domainPart.endsWith("-") || domainPart.contains("..")) {
            throw new InvalidEmailDomainValidation("Domain cannot start/end with a hyphen or contain consecutive dots");
        }
        if (!domainPart.matches("[a-zA-Z0-9.-]+")) {
            throw new InvalidEmailDomainValidation("Domain contains invalid characters");
        }

        //final validation
        if(!EMAIL_PATTERN.matcher(email).matches()){
            throw new InvalidEmailDomainValidation("Invalid email format");
        }



    }
}
