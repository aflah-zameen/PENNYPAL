package com.application.pennypal.application.service.goal;

import com.application.pennypal.application.mappers.goal.GoalApplicationMapper;
import com.application.pennypal.application.dto.output.goal.GoalResponseOutput;
import com.application.pennypal.application.port.out.repository.GoalContributionRepositoryPort;
import com.application.pennypal.application.port.out.repository.GoalRepositoryPort;
import com.application.pennypal.application.port.out.repository.TransactionRepositoryPort;
import com.application.pennypal.application.port.in.goal.GetAllGoals;
import com.application.pennypal.domain.goal.entity.Goal;
import com.application.pennypal.domain.goal.entity.GoalContribution;
import com.application.pennypal.domain.transaction.entity.Transaction;
import com.application.pennypal.domain.valueObject.TransactionType;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetAllGoalsService implements GetAllGoals {
    private final GoalRepositoryPort goalRepositoryPort;
    private final GoalApplicationMapper goalApplicationMapper;
    private final GoalContributionRepositoryPort goalContributionRepositoryPort;
    @Override
    public List<GoalResponseOutput> execute(String userId) {
            List<Goal> goals = goalRepositoryPort.getAllNonDeletedGoals(userId);
            return goals.stream()
                    .map(goal -> {
                        List<GoalContribution> contributionList = goalContributionRepositoryPort.getAllContributions(userId,goal.getGoalId());
                        return goalApplicationMapper.toOutput(goal,contributionList);
                    })
                    .toList();
    }
}
