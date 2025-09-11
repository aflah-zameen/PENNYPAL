package com.application.pennypal.application.service.reward;

import com.application.pennypal.application.port.in.reward.GetRewardAmount;
import com.application.pennypal.application.port.out.repository.RewardPolicyRepositoryPort;
import com.application.pennypal.domain.reward.RewardActionType;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
@RequiredArgsConstructor
public class GetRewardAmountService implements GetRewardAmount {
    private final RewardPolicyRepositoryPort rewardPolicyRepositoryPort;
    @Override
    public BigDecimal get(RewardActionType actionType) {
        return rewardPolicyRepositoryPort.getRewardAmount(actionType);
    }
}
