package com.application.pennypal.domain.validator;

import java.util.Set;
import java.util.regex.Pattern;

public class NameValidator {
    private static final Pattern NAME_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9\\s\\-'_]{2,50}$"
    );
    private static final Set<String> RESERVED_WORDS = Set.of(
            "admin", "guest", "system", "root", "user"
    );

    public static void validate(String name) throws IllegalArgumentException{
        //empty validation
        if(name == null){
            throw new IllegalArgumentException("Name cannot be null");
        }
        if(name.trim().isEmpty()){
            throw new IllegalArgumentException("Name cannot be empty or whitespace");
        }

        //name length validation
        if(name.length() > 50){
            throw new IllegalArgumentException("Name exceeds maximum length of 50 characters");
        }
        if(name.length() < 3){
            throw new IllegalArgumentException("Name must be at least 3 characters long");
        }

        //invalid characters
        if (!name.matches("[a-zA-Z0-9\\s\\-'_]+")) {
            throw new IllegalArgumentException("Name contains invalid characters");
        }

        //Leading/trailing spaces
        if (name.startsWith(" ") || name.endsWith(" ")) {
            throw new IllegalArgumentException("Name cannot start or end with a space");
        }

        //reserved name
        if (RESERVED_WORDS.contains(name.toLowerCase())) {
            throw new IllegalArgumentException("Name is a reserved word");
        }

        // 8. Final regex check
        if (!NAME_PATTERN.matcher(name).matches()) {
            throw new IllegalArgumentException("Invalid name format");
        }

    }
}
