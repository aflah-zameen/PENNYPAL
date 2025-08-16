package com.application.pennypal.application.port.out.service;

import java.util.Optional;

public interface VerificationTokenServicePort {
    String getToken(String email);
    boolean isValid(String token,String email);
    void saveToken(String token,String email);
    Optional<String> getEmail(String token);
    void deleteToken(String token);
    void deleteUsingEmail(String email);
}
