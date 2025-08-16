package com.application.pennypal.application.port.out.repository;

import com.application.pennypal.application.dto.output.goal.GoalWithdrawOutput;
import com.application.pennypal.domain.goal.entity.GoalWithdraw;

import java.util.List;
import java.util.Optional;

public interface GoalWithdrawRepositoryPort {
    GoalWithdraw save(GoalWithdraw withdrawRequest);

    long findPendingRequestCount();
    Optional<GoalWithdraw> findGoalWithdrawById(String withdrawId);
    void update(GoalWithdraw goalWithdraw);

    List<GoalWithdrawOutput> findAllGoalWithdrawRequests();
}
