package com.application.pennypal.application.service.goal;

import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.dto.input.goal.EditGoalInputModel;
import com.application.pennypal.application.mappers.goal.GoalApplicationMapper;
import com.application.pennypal.application.port.out.repository.GoalRepositoryPort;
import com.application.pennypal.application.port.in.goal.EditGoal;
import com.application.pennypal.domain.goal.entity.Goal;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class EditGoalService implements EditGoal {
    private final GoalRepositoryPort goalRepositoryPort;
    private final GoalApplicationMapper goalApplicationMapper;
    @Override
    public void execute(String userId, EditGoalInputModel editGoalInputModel) {
        Goal goal = goalRepositoryPort.getGoalById(editGoalInputModel.goalId())
                .orElseThrow(() ->new ApplicationBusinessException("Goal is not found","NOT_FOUND"));
        if(goal.getUserId().equals(userId)){
            Duration duration = Duration.between(goal.getCreatedAt(),LocalDateTime.now());
            if(duration.toMinutes() >30){
                throw new ApplicationBusinessException("You can't  edit the goal after 30 minutes of creation", "EDIT_RESTRICTED");
            }

            Goal updatedGoal = goalApplicationMapper.toDomain(editGoalInputModel,userId);
            updatedGoal.setDeleted(goal.isDeleted());
            updatedGoal.setStatus(goal.getStatus());
            updatedGoal.setCurrentAmount(goal.getCurrentAmount());
            updatedGoal.setCategoryId(goal.getCategoryId());
            updatedGoal.setStartDate(goal.getStartDate());
            updatedGoal.setEndDate(goal.getEndDate());
            updatedGoal.setDescription(goal.getDescription());
            updatedGoal.setTitle(goal.getTitle());
            updatedGoal.setTargetAmount(goal.getTargetAmount());
            updatedGoal.setPriorityLevel(goal.getPriorityLevel());

            goalRepositoryPort.update(updatedGoal,userId);
        }else{
            throw new ApplicationBusinessException("User action is not authenticated","UNAUTHENTICATED_ACTION");
        }
    }
}
