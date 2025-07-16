package com.application.pennypal.application.service.goal;

import com.application.pennypal.application.mappers.goal.GoalApplicationMapper;
import com.application.pennypal.application.output.goal.GoalResponseOutput;
import com.application.pennypal.application.port.GoalRepositoryPort;
import com.application.pennypal.application.port.TransactionRepositoryPort;
import com.application.pennypal.application.usecases.goal.GetAllGoals;
import com.application.pennypal.domain.entity.Goal;
import com.application.pennypal.domain.entity.Transactions;
import com.application.pennypal.domain.valueObject.TransactionOriginType;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetAllGoalsService implements GetAllGoals {
    private final GoalRepositoryPort goalRepositoryPort;
    private final GoalApplicationMapper goalApplicationMapper;
    private final TransactionRepositoryPort transactionRepositoryPort;
    @Override
    public List<GoalResponseOutput> execute(Long userId) {
            List<Goal> goals = goalRepositoryPort.getAllNonDeletedGoals(userId);
            return goals.stream()
                    .map(goal -> {
                        List<Transactions> contributionList = transactionRepositoryPort.findAllByUserIdAndOriginIdAndType(userId,goal.getId(), TransactionOriginType.GOAL);
                        return goalApplicationMapper.toOutput(goal,contributionList);
                    })
                    .toList();
    }
}
