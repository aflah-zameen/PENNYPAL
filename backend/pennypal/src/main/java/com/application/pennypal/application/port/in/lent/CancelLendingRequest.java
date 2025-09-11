package com.application.pennypal.application.port.in.lent;

public interface CancelLendingRequest {
    void execute(String userId,String requestId);
}
