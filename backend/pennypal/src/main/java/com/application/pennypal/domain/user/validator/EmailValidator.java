package com.application.pennypal.domain.user.validator;

import java.util.regex.Pattern;

public class EmailValidator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._%+-]{1,64}(?<!\\.)@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
    );

    public static void validate(String email) throws IllegalArgumentException{


        if(email == null || email.trim().isEmpty()){
            throw new IllegalArgumentException("Email cannot be null");
        }
        if (email.length() > 254) {
            throw new IllegalArgumentException("Email exceeds maximum length of 254 characters");
        }

        int atCount = email.length() - email.replace("@","").length();
        if (atCount != 1) {
            throw new IllegalArgumentException("Email must contain exactly one @ symbol");
        }

        // Split into local part and domain
        String[] parts = email.split("@");
        String localPart = parts[0];
        String domainPart = parts[1];

        //validate local part
        if (localPart.isEmpty()) {
            throw new IllegalArgumentException("Local part cannot be empty");
        }
        if (localPart.length() > 64) {
            throw new IllegalArgumentException("Local part exceeds maximum length of 64 characters");
        }
        if (localPart.startsWith(".") || localPart.endsWith(".") || localPart.contains("..")) {
            throw new IllegalArgumentException("Local part cannot start/end with a dot or contain consecutive dots");
        }
        if (!localPart.matches("[a-zA-Z0-9._%+-]+")) {
            throw new IllegalArgumentException("Local part contains invalid characters");
        }

        //validate domain part
        if (domainPart.isEmpty()) {
            throw new IllegalArgumentException("Domain cannot be empty");
        }
        if (!domainPart.contains(".")) {
            throw new IllegalArgumentException("Domain must contain a top-level domain");
        }
        if (domainPart.startsWith("-") || domainPart.endsWith("-") || domainPart.contains("..")) {
            throw new IllegalArgumentException("Domain cannot start/end with a hyphen or contain consecutive dots");
        }
        if (!domainPart.matches("[a-zA-Z0-9.-]+")) {
            throw new IllegalArgumentException("Domain contains invalid characters");
        }

        //final validation
        if(!EMAIL_PATTERN.matcher(email).matches()){
            throw new IllegalArgumentException("Invalid email format");
        }



    }
}
