package com.application.pennypal.application.port.in.subscription;

public interface HasActiveSubscription {
    boolean execute(String email);
}
