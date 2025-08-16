package com.application.pennypal.infrastructure.persistence.jpa.mapper;

import com.application.pennypal.domain.goal.entity.Goal;
import com.application.pennypal.infrastructure.persistence.jpa.entity.CategoryEntity;
import com.application.pennypal.infrastructure.persistence.jpa.entity.GoalEntity;
import com.application.pennypal.infrastructure.persistence.jpa.entity.UserEntity;

public class GoalJpaMapper {

    public static Goal toDomain(GoalEntity goalEntity){
        return Goal.reconstruct(
                goalEntity.getGoalId(),
                goalEntity.getUser().getUserId(),
                goalEntity.getTitle(),
                goalEntity.getTargetAmount(),
                goalEntity.getCurrentAmount(),
                goalEntity.getStartDate(),
                goalEntity.getEndDate(),
                goalEntity.getCategory().getCategoryId(),
                goalEntity.getPriorityLevel(),
                goalEntity.getDescription(),
                goalEntity.getStatus(),
                goalEntity.isDeleted(),
                goalEntity.getCreatedAt(),
                goalEntity.getUpdatedAt()
        );
    }


    public static GoalEntity toEntity(Goal goal, UserEntity user, CategoryEntity category){
        return new GoalEntity(
                goal.getGoalId(),
                user,
                goal.getTitle(),
                goal.getDescription(),
                goal.getTargetAmount(),
                goal.getCurrentAmount(),
                goal.getStartDate(),
                goal.getEndDate(),
                goal.getStatus(),
                category,
                goal.getPriorityLevel(),
                goal.isDeleted()
        );
    }

}
