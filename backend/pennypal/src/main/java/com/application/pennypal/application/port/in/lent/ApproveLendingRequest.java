package com.application.pennypal.application.port.in.lent;

public interface ApproveLendingRequest {
    void execute(String userId,String requestId);
}
