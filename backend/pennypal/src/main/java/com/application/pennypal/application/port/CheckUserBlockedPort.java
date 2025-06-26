package com.application.pennypal.application.port;

public interface CheckUserBlockedPort {
    boolean check(String email);
}
