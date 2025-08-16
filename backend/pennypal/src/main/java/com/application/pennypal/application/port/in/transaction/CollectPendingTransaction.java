package com.application.pennypal.application.port.in.transaction;

public interface CollectPendingTransaction {
    void collect(String userId, String recurringLogId);
}
