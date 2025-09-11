package com.application.pennypal.application.port.in.lent;

public interface RejectLendingRequest {
    void execute(String userId, String requestId);
}
