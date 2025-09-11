package com.application.pennypal.application.port.in.reward;

import com.application.pennypal.application.dto.output.reward.RewardPolicyOutputModel;
import com.application.pennypal.domain.reward.RewardActionType;

import java.math.BigDecimal;

public interface SetReward {
    RewardPolicyOutputModel execute(RewardActionType actionType, BigDecimal amount);
}
