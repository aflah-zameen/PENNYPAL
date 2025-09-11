package com.application.pennypal.infrastructure.adapter.out.subscription;

import com.application.pennypal.application.port.out.repository.SubscriptionPlanRepositoryPort;
import com.application.pennypal.domain.subscription.SubscriptionPlan;
import com.application.pennypal.infrastructure.exception.base.InfrastructureException;
import com.application.pennypal.infrastructure.persistence.jpa.mapper.SubscriptionJpaMapper;
import com.application.pennypal.infrastructure.persistence.jpa.subscription.SubscriptionPlanEntity;
import com.application.pennypal.infrastructure.persistence.jpa.subscription.SubscriptionPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
@RequiredArgsConstructor
public class SubscriptionPlanRepositoryAdapter implements SubscriptionPlanRepositoryPort {
    private final SubscriptionPlanRepository subscriptionPlanRepository;
    @Override
    public Optional<SubscriptionPlan> getPlan(String planId) {
        return subscriptionPlanRepository.findByPlanId(planId).map(SubscriptionJpaMapper::toDomain);
    }

    @Override
    public List<SubscriptionPlan> getAllPlan() {
       return subscriptionPlanRepository.findAllByDeletedFalse().stream()
                .map(SubscriptionJpaMapper::toDomain)
                .toList();
    }

    @Override
    public SubscriptionPlan save(SubscriptionPlan subscriptionPlan) {
        SubscriptionPlanEntity entity =  SubscriptionJpaMapper.toEntity(subscriptionPlan);
        entity = subscriptionPlanRepository.save(entity);
        return SubscriptionJpaMapper.toDomain(entity);
    }

    @Override
    public SubscriptionPlan update(SubscriptionPlan plan) {
        SubscriptionPlanEntity entity = subscriptionPlanRepository.findByPlanId(plan.getPlanId())
                .orElseThrow(() -> new InfrastructureException("Plan jpa entity not found","NOT_FOUND"));
        entity.setName(plan.getName());
        entity.setDescription(plan.getDescription());
        entity.setPrice(plan.getAmount());
        entity.setFeatures(plan.getFeatures());
        entity.setDurationDays(plan.getDurationDays());
        entity.setDeleted(plan.isDeleted());
        entity = subscriptionPlanRepository.save(entity);
        return SubscriptionJpaMapper.toDomain(entity);
    }
}
