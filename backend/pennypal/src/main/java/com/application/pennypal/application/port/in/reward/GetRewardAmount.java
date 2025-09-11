package com.application.pennypal.application.port.in.reward;

import com.application.pennypal.domain.reward.RewardActionType;

import java.math.BigDecimal;

public interface GetRewardAmount {
    BigDecimal get(RewardActionType actionType);
}
