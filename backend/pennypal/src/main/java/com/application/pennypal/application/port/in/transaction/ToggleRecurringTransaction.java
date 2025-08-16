package com.application.pennypal.application.port.in.transaction;

public interface ToggleRecurringTransaction {
    void execute(String userId,String recurringId);
}
