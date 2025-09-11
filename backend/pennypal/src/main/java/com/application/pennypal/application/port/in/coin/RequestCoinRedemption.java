package com.application.pennypal.application.port.in.coin;

import com.application.pennypal.application.dto.output.coin.RedemptionHistoryOutputModel;

import java.math.BigDecimal;

public interface RequestCoinRedemption {
    RedemptionHistoryOutputModel execute(String userId,BigDecimal coinAmount,BigDecimal realMoney);
}
