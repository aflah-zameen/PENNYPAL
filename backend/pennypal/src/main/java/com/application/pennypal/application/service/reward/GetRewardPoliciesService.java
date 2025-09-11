package com.application.pennypal.application.service.reward;

import com.application.pennypal.application.dto.output.reward.RewardPolicyOutputModel;
import com.application.pennypal.application.port.in.reward.GetRewardPolicies;
import com.application.pennypal.application.port.out.repository.RewardPolicyRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetRewardPoliciesService implements GetRewardPolicies {
    private final RewardPolicyRepositoryPort rewardPolicyRepositoryPort;
    @Override
    public List<RewardPolicyOutputModel> execute() {
            return rewardPolicyRepositoryPort.getRewardPolicies().stream()
                    .map(rewardPolicy -> new RewardPolicyOutputModel(
                            rewardPolicy.getId(),
                            rewardPolicy.getActionType().name(),
                            rewardPolicy.getCoinAmount(),
                            rewardPolicy.getCreatedAt(),
                            rewardPolicy.isActive(),
                            rewardPolicy.isDeleted()
                    ))
                    .toList();
    }
}
