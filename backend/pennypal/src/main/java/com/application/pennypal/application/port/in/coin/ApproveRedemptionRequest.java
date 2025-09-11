package com.application.pennypal.application.port.in.coin;

import java.math.BigDecimal;

public interface ApproveRedemptionRequest {
    BigDecimal execute(String userId, String redemptionId);
}
