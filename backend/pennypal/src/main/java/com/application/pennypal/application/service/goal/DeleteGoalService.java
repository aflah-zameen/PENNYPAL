package com.application.pennypal.application.service.goal;

import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.port.out.repository.GoalRepositoryPort;
import com.application.pennypal.application.port.in.goal.DeleteGoal;
import com.application.pennypal.domain.goal.entity.Goal;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteGoalService implements DeleteGoal {
    private final GoalRepositoryPort goalRepositoryPort;
    @Override
    public void execute(String userId,String goalId) {
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
