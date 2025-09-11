package com.application.pennypal.infrastructure.adapter.out.reward;

import com.application.pennypal.application.port.out.repository.RewardPolicyRepositoryPort;
import com.application.pennypal.domain.reward.RewardActionType;
import com.application.pennypal.domain.reward.RewardPolicy;
import com.application.pennypal.infrastructure.persistence.jpa.mapper.RewardJpaMapper;
import com.application.pennypal.infrastructure.persistence.jpa.reward.RewardPolicyEntity;
import com.application.pennypal.infrastructure.persistence.jpa.reward.RewardPolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RewardPolicyRepositoryAdapter implements RewardPolicyRepositoryPort {
    private final RewardPolicyRepository rewardPolicyRepository;

    @Override
    public List<RewardPolicy> getRewardPolicies() {
        return rewardPolicyRepository.findAllByDeletedFalse().stream()
                .map(RewardJpaMapper::toDomain)
                .toList();
    }

    @Override
    public BigDecimal getRewardAmount(RewardActionType actionType) {
        return rewardPolicyRepository.findCoinAmountByActionType(actionType)
                .orElse(BigDecimal.ZERO);
    }


    @Override
    public Optional<RewardPolicy> findByActionType(RewardActionType actionType) {
        return rewardPolicyRepository.findByActionType(actionType).map(RewardJpaMapper::toDomain);
    }

    @Override
    public RewardPolicy save(RewardPolicy rewardPolicy) {
        RewardPolicyEntity entity = RewardJpaMapper.toEntity(rewardPolicy);
        entity = rewardPolicyRepository.save(entity);
        return RewardJpaMapper.toDomain(entity);
    }

    @Override
    public Optional<RewardPolicy> findById(String id) {
        return rewardPolicyRepository.findById(id).map(RewardJpaMapper::toDomain);
    }
}
