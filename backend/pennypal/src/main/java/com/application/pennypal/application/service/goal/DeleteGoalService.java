package com.application.pennypal.application.service.goal;

import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.port.GoalRepositoryPort;
import com.application.pennypal.application.usecases.goal.DeleteGoal;
import com.application.pennypal.domain.entity.Goal;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteGoalService implements DeleteGoal {
    private final GoalRepositoryPort goalRepositoryPort;
    @Override
    public void execute(Long userId,Long goalId) {
        Goal goal = goalRepositoryPort.getGoalById(goalId)
                .orElseThrow(() -> new ApplicationBusinessException("Goal cannot be found","NOT_FOUND"));
        if(goal.getUserId().equals(userId)){
            goal.setDeleted(true);
            goalRepositoryPort.save(goal);
        }else {
            throw new ApplicationBusinessException("User action is not authenticated","UNAUTHENTICATED_ACTION");
        }
    }
}
