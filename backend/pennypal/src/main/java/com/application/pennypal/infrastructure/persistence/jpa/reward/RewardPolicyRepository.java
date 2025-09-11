package com.application.pennypal.infrastructure.persistence.jpa.reward;

import com.application.pennypal.domain.reward.RewardActionType;
import com.application.pennypal.domain.reward.RewardPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface RewardPolicyRepository extends JpaRepository<RewardPolicyEntity,String> {
    Optional<RewardPolicyEntity> findByActionType(RewardActionType actionType);

    List<RewardPolicyEntity> findAllByDeletedFalse();

    @Query("SELECT r.coinAmount FROM RewardPolicyEntity r " +
            "WHERE r.actionType = :actionType AND r.active = true AND r.deleted = false")
    Optional<BigDecimal> findCoinAmountByActionType(@Param("actionType") RewardActionType actionType);
}
