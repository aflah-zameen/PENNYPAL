package com.application.pennypal.application.port.in.user;

public interface CheckUserIsSuspended {
    boolean execute(String email);
}
