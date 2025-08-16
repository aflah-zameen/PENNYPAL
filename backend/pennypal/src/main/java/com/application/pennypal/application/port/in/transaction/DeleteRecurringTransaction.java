package com.application.pennypal.application.port.in.transaction;

public interface DeleteRecurringTransaction {
    void execute(String userId, String recurringId);
}
