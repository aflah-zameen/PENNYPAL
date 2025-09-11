package com.application.pennypal.application.port.in.coin;

public interface RejectRedemptionRequest {
    void execute(String userId, String redemptionId);
}
