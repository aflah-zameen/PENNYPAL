package com.application.pennypal.application.service.goal;

import com.application.pennypal.application.output.goal.GoalSummaryOutputModel;
import com.application.pennypal.application.port.GoalRepositoryPort;
import com.application.pennypal.application.usecases.goal.GetGoalSummary;
import com.application.pennypal.domain.valueObject.GoalStatus;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class GetGoalSummaryService implements GetGoalSummary {
    private final GoalRepositoryPort goalRepositoryPort;
    @Override
    public GoalSummaryOutputModel execute(Long userId) {

        /// Computing Goal summary stats
        Long totalActiveGoals = goalRepositoryPort.getTotalGoalsByUserIdAndStatus(userId, GoalStatus.ACTIVE);
        Long totalCompletedGoals= goalRepositoryPort.getTotalGoalsByUserIdAndStatus(userId,GoalStatus.COMPLETED);
        BigDecimal totalAmountSaved = goalRepositoryPort.getTotalAmountSaved(userId);

        return new GoalSummaryOutputModel(totalActiveGoals,totalAmountSaved,totalCompletedGoals);
    }
}
