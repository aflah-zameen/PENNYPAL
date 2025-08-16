package com.application.pennypal.application.port.out.service;

public interface EncodePasswordPort {
    String encode(String password);
    boolean matches(String rawPassword, String encodedPassword);
}
