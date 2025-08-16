package com.application.pennypal.application.port.out.repository;

import com.application.pennypal.application.dto.input.goal.GoalAdminFilter;
import com.application.pennypal.application.dto.output.goal.AdminGoalResponseOutput;
import com.application.pennypal.application.dto.output.goal.AdminGoalStats;
import com.application.pennypal.application.dto.output.goal.GoalResponseOutput;
import com.application.pennypal.application.dto.output.paged.PagedResultOutput;
import com.application.pennypal.domain.goal.entity.Goal;
import com.application.pennypal.domain.valueObject.GoalStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface GoalRepositoryPort {
    Goal save(Goal goal);
    Goal update(Goal update,String goalId);
    List<Goal> getAllNonDeletedGoals(String userId);

    Optional<Goal> getGoalById(String goalId);

    Long getTotalGoalsByUserIdAndStatus(String userId, GoalStatus goalStatus);

    BigDecimal getTotalAmountSaved(String userId);

    PagedResultOutput<AdminGoalResponseOutput> fetchFilteredAdminGoals(GoalAdminFilter adminFilter, int page, int size);

    AdminGoalStats getAdminGoalStats();
}
