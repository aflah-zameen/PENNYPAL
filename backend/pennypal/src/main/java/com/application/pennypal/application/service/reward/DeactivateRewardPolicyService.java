package com.application.pennypal.application.service.reward;

import com.application.pennypal.application.dto.output.reward.RewardPolicyOutputModel;
import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.port.in.reward.DeactivateRewardPolicy;
import com.application.pennypal.application.port.out.repository.RewardPolicyRepositoryPort;
import com.application.pennypal.domain.reward.RewardPolicy;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeactivateRewardPolicyService implements DeactivateRewardPolicy {
    private final RewardPolicyRepositoryPort rewardPolicyRepositoryPort;
    @Override
    public RewardPolicyOutputModel execute(String id) {
        RewardPolicy rewardPolicy = rewardPolicyRepositoryPort.findById(id)
                .orElseThrow(() -> new ApplicationBusinessException("Reward policy not found","NOT_FOUND"));
        rewardPolicy.deactivate();
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
