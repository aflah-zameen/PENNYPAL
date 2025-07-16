package com.application.pennypal.application.port;

import com.application.pennypal.domain.entity.Goal;
import com.application.pennypal.domain.valueObject.GoalStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface GoalRepositoryPort {
    Goal save(Goal goal);
    List<Goal> getAllNonDeletedGoals(Long userId);

    Optional<Goal> getGoalById(Long goalId);

    Long getTotalGoalsByUserIdAndStatus(Long userId, GoalStatus goalStatus);

    BigDecimal getTotalAmountSaved(Long userId);
}
