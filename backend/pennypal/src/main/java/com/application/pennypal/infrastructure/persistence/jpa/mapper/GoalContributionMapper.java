package com.application.pennypal.infrastructure.persistence.jpa.mapper;

import com.application.pennypal.domain.goal.entity.GoalContribution;
import com.application.pennypal.infrastructure.persistence.jpa.entity.GoalContributionEntity;

public class GoalContributionMapper {
    public static GoalContribution toDomain(GoalContributionEntity entity){
        return GoalContribution.reconstruct(
                entity.getContributionId(),
                entity.getUserId(),
                entity.getGoalId(),
                entity.getCardId(),
                entity.getTransactionId(),
                entity.getAmount(),
                entity.getDate(),
                entity.getNotes()
        );
    }

    public static GoalContributionEntity toEntity(GoalContribution contribution){
        return new GoalContributionEntity(
                contribution.getContributionId(),
                contribution.getUserId(),
                contribution.getGoalId(),
                contribution.getTransactionId(),
                contribution.getAmount(),
                contribution.getDate()
        );
    }
}
