package com.application.pennypal.application.service.goal;

import com.application.pennypal.application.exception.BusinessException;
import com.application.pennypal.application.input.goal.EditGoalInputModel;
import com.application.pennypal.application.mappers.goal.GoalApplicationMapper;
import com.application.pennypal.application.port.GoalRepositoryPort;
import com.application.pennypal.application.usecases.goal.EditGoal;
import com.application.pennypal.domain.entity.Goal;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class EditGoalService implements EditGoal {
    private final GoalRepositoryPort goalRepositoryPort;
    private final GoalApplicationMapper goalApplicationMapper;
    @Override
    public void execute(Long userId, EditGoalInputModel editGoalInputModel) {
        Goal goal = goalRepositoryPort.getGoalById(editGoalInputModel.id())
                .orElseThrow(() ->new BusinessException("Goal is not found","NOT_FOUND"));
        if(goal.getUserId().equals(userId)){
            Duration duration = Duration.between(goal.getCreatedAt(),LocalDateTime.now());
            if(duration.toMinutes() >30){
                throw new BusinessException("You can't  edit the goal after 30 minutes of creation", "EDIT_RESTRICTED");
            }

            Goal updatedGoal = goalApplicationMapper.toDomain(editGoalInputModel,userId);
            updatedGoal.setDeleted(goal.isDeleted());
            updatedGoal.setStatus(goal.getStatus());
            updatedGoal.setCreatedAt(goal.getCreatedAt());
            updatedGoal.setUpdatedAt(goal.getUpdatedAt());
            updatedGoal.setCurrentAmount(goal.getCurrentAmount());

            goalRepositoryPort.save(updatedGoal);
        }else{
            throw new BusinessException("User action is not authenticated","UNAUTHENTICATED_ACTION");
        }
    }
}
