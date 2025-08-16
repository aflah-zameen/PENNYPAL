package com.application.pennypal.infrastructure.persistence.jpa.goal;

import com.application.pennypal.infrastructure.persistence.jpa.entity.GoalContributionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoalContributionRepository extends JpaRepository<GoalContributionEntity,Long> {
    List<GoalContributionEntity> findAllByUserIdAndGoalId(String userId, String goalId);
}
