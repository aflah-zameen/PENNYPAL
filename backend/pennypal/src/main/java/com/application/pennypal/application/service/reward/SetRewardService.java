package com.application.pennypal.application.service.reward;

import com.application.pennypal.application.dto.output.reward.RewardPolicyOutputModel;
import com.application.pennypal.application.port.in.reward.*;
import com.application.pennypal.application.port.out.repository.RewardPolicyRepositoryPort;
import com.application.pennypal.domain.reward.RewardActionType;
import com.application.pennypal.domain.reward.RewardPolicy;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@RequiredArgsConstructor
public class SetRewardService implements SetReward {
    private final RewardPolicyRepositoryPort rewardPolicyRepositoryPort;
    @Override
    public RewardPolicyOutputModel execute(RewardActionType actionType, BigDecimal amount) {
        RewardPolicy rewardPolicy = rewardPolicyRepositoryPort.findByActionType(actionType)
                .orElse(RewardPolicy.builder()
                        .id("REW_"+ UUID.randomUUID())
                        .actionType(actionType)
                        .active(true)
                        .deleted(false)
                        .coinAmount(amount)
                        .build());

        rewardPolicy.updateCoinAmount(amount);

        rewardPolicy =  rewardPolicyRepositoryPort.save(rewardPolicy);
        return new RewardPolicyOutputModel(
                rewardPolicy.getId(),
                rewardPolicy.getActionType().name(),
                rewardPolicy.getCoinAmount(),
                rewardPolicy.getCreatedAt(),
                rewardPolicy.isActive(),
                rewardPolicy.isDeleted()
        );
    }
}
