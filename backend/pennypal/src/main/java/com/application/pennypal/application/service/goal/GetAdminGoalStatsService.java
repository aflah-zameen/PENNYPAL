package com.application.pennypal.application.service.goal;

import com.application.pennypal.application.dto.output.goal.AdminGoalStats;
import com.application.pennypal.application.port.in.goal.GetAdminGoalStats;
import com.application.pennypal.application.port.out.repository.GoalRepositoryPort;
import com.application.pennypal.application.port.out.repository.GoalWithdrawRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetAdminGoalStatsService implements GetAdminGoalStats {
    private final GoalRepositoryPort goalRepositoryPort;
    private final GoalWithdrawRepositoryPort withdrawRepositoryPort;
    @Override
    public AdminGoalStats execute() {
        AdminGoalStats stats  =  goalRepositoryPort.getAdminGoalStats();
        long pendingRequest = withdrawRepositoryPort.findPendingRequestCount();
        return new AdminGoalStats(
                stats.totalGoals(),
                stats.activeGoals(),
                stats.completedGoals(),
                stats.totalContributions(),
                pendingRequest
        );
    }
}
