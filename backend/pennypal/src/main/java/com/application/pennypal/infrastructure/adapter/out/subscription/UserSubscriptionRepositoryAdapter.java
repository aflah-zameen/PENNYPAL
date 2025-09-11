package com.application.pennypal.infrastructure.adapter.out.subscription;

import com.application.pennypal.application.dto.output.sale.SubscriptionBreakdownOutput;
import com.application.pennypal.application.port.out.repository.UserSubscriptionRepositoryPort;
import com.application.pennypal.domain.subscription.UserSubscription;
import com.application.pennypal.infrastructure.exception.base.InfrastructureException;
import com.application.pennypal.infrastructure.persistence.jpa.mapper.SubscriptionJpaMapper;
import com.application.pennypal.infrastructure.persistence.jpa.subscription.SubscriptionPlanEntity;
import com.application.pennypal.infrastructure.persistence.jpa.subscription.SubscriptionPlanRepository;
import com.application.pennypal.infrastructure.persistence.jpa.subscription.UserSubscriptionEntity;
import com.application.pennypal.infrastructure.persistence.jpa.subscription.UserSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserSubscriptionRepositoryAdapter implements UserSubscriptionRepositoryPort {
    private final UserSubscriptionRepository userSubscriptionRepository;
    private final SubscriptionPlanRepository subscriptionPlanRepository;
    @Override
    public boolean existsUser(String userId) {
       return userSubscriptionRepository.existsByUserIdAndIsActiveTrue(userId);
    }

    @Override
    public UserSubscription save(UserSubscription sub, String planId) {
        SubscriptionPlanEntity plan = subscriptionPlanRepository.findByPlanId(planId)
                .orElseThrow(() -> new InfrastructureException("Plan not found","NOT_FOUND"));
        UserSubscriptionEntity entity = SubscriptionJpaMapper.toEntity(sub,plan);
        entity =  userSubscriptionRepository.save(entity);
        return SubscriptionJpaMapper.toDomain(entity);
    }

    @Override
    public boolean hasActiveSubscription(String userId, LocalDate today) {
        return userSubscriptionRepository.existsByUserIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualAndIsActiveTrue(
                userId, today, today
        );    }

    @Override
    public List<UserSubscription> findByPlanIdAndDateRange(String planId, LocalDate start, LocalDate end) {
        return userSubscriptionRepository.findByPlanIdAndDateRange(planId,start,end);
    }

    @Override
    public int countByIsActiveTrue() {
        return userSubscriptionRepository.countByIsActiveTrue();
    }

    @Override
    public List<SubscriptionBreakdownOutput> findBreakdownByPlan(LocalDate start, LocalDate end) {
        LocalDateTime startDateTime = start.atStartOfDay();
        LocalDateTime endDateTime = end.atTime(LocalTime.MAX);
        return userSubscriptionRepository.findBreakdownByPlan(startDateTime,endDateTime);
    }

    @Override
    public List<UserSubscription> findByDateRangeAndPlans(LocalDate start, LocalDate end, List<String> planIds) {
        LocalDateTime startDateTime = start.atStartOfDay();
        LocalDateTime endDateTime = end.atTime(LocalTime.MAX);
        return userSubscriptionRepository.findByDateRangeAndPlans(startDateTime,endDateTime,planIds)
                .stream()
                .map(SubscriptionJpaMapper::toDomain)
                .toList();
    }
}
