package com.application.pennypal.application.port.out.repository;

import com.application.pennypal.domain.goal.entity.GoalContribution;

import java.util.List;

public interface GoalContributionRepositoryPort {
    GoalContribution save(GoalContribution contribution);
    List<GoalContribution> getAllContributions(String userId,String goalId);
}
