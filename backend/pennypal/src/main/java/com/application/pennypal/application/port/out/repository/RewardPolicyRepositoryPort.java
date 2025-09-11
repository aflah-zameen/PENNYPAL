package com.application.pennypal.application.port.out.repository;

import com.application.pennypal.domain.reward.RewardActionType;
import com.application.pennypal.domain.reward.RewardPolicy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface RewardPolicyRepositoryPort {
    List<RewardPolicy> getRewardPolicies();

    BigDecimal getRewardAmount(RewardActionType actionType);

    Optional<RewardPolicy> findByActionType(RewardActionType actionType);

    RewardPolicy save(RewardPolicy rewardPolicy);

    Optional<RewardPolicy> findById(String id);
}
