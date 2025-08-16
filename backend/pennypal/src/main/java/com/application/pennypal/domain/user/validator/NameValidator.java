package com.application.pennypal.domain.user.validator;

import com.application.pennypal.domain.user.exception.validation.InvalidNameDomainException;
import com.application.pennypal.domain.user.exception.validation.MissingNameDomainException;

import java.util.Set;
import java.util.regex.Pattern;

public class NameValidator {
    private static final Pattern NAME_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9\\s\\-'_]{2,50}$"
    );
    private static final Set<String> RESERVED_WORDS = Set.of(
            "admin", "guest", "system", "root", "user"
    );

    public static void validate(String name) {
        //empty validation
        if(name == null){
            throw new MissingNameDomainException("Name cannot be null");
        }
        if(name.trim().isEmpty()){
            throw new InvalidNameDomainException("Name cannot be empty or whitespace");
        }

        //name length validation
        if(name.length() > 50){
            throw new InvalidNameDomainException("Name exceeds maximum length of 50 characters");
        }
        if(name.length() < 3){
            throw new InvalidNameDomainException("Name must be at least 3 characters long");
        }

        //invalid characters
        if (!name.matches("[a-zA-Z0-9\\s\\-'_]+")) {
            throw new InvalidNameDomainException("Name contains invalid characters");
        }

        //Leading/trailing spaces
        if (name.startsWith(" ") || name.endsWith(" ")) {
            throw new InvalidNameDomainException("Name cannot start or end with a space");
        }

        //reserved name
        if (RESERVED_WORDS.contains(name.toLowerCase())) {
            throw new InvalidNameDomainException("Name is a reserved word");
        }

        // 8. Final regex check
        if (!NAME_PATTERN.matcher(name).matches()) {
            throw new InvalidNameDomainException("Invalid name format");
        }

    }
}
