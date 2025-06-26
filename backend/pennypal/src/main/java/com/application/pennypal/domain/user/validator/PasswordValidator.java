package com.application.pennypal.domain.user.validator;

public class PasswordValidator {

    public static void validate(String password) throws IllegalArgumentException{
        if(password == null){
            throw new IllegalArgumentException("Password cannot be null");
        }
        if(password.length() < 8){
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }
        if(!password.matches(".*[a-z].*")) {
            throw new IllegalArgumentException("Password must contain at least one lowercase letter");
        }
        if(!password.matches(".*[A-Z].*")){
            throw new IllegalArgumentException("Password must contain at least one uppercase letter");
        }
        if (!password.matches(".*\\d.*")) {
            throw new IllegalArgumentException("Password must contain at least one number");
        }
        if (!password.matches(".*[@#$%^&*()!].*")) {
            throw new IllegalArgumentException("Password must contain at least one special character");
        }
    }
}
