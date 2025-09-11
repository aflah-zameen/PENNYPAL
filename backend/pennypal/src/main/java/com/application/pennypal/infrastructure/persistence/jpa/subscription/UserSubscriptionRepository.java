package com.application.pennypal.infrastructure.persistence.jpa.subscription;

import com.application.pennypal.application.dto.output.sale.SubscriptionBreakdownOutput;
import com.application.pennypal.domain.subscription.UserSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface UserSubscriptionRepository extends JpaRepository<UserSubscriptionEntity,Long> {
    boolean existsByUserIdAndIsActiveTrue(String userId);
    boolean existsByUserIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualAndIsActiveTrue(String userId, LocalDate today, LocalDate today1);

    @Query("SELECT t FROM UserSubscriptionEntity t " +
            "WHERE t.plan.planId = :planId " +
            "AND t.startDate <= :end " +
            "AND t.endDate >= :start")
    List<UserSubscription> findByPlanIdAndDateRange(String planId, LocalDate start, LocalDate end);

    int countByIsActiveTrue();

    @Query("SELECT new com.application.pennypal.application.dto.output.sale.SubscriptionBreakdownOutput(s.plan.planId, COUNT(s), SUM(s.plan.price)) FROM UserSubscriptionEntity s WHERE s.createdAt BETWEEN :start AND :end GROUP BY s.plan.planId")
    List<SubscriptionBreakdownOutput> findBreakdownByPlan(LocalDateTime start, LocalDateTime end);

    @Query("SELECT u FROM UserSubscriptionEntity u WHERE u.createdAt BETWEEN :start AND :end AND u.plan.planId IN :planIds")
    List<UserSubscriptionEntity> findByDateRangeAndPlans(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("planIds") List<String> planIds
    );

}
