package com.application.pennypal.application.service.goal;

import com.application.pennypal.application.input.goal.AddGoalInputModel;
import com.application.pennypal.application.mappers.goal.GoalApplicationMapper;
import com.application.pennypal.application.output.goal.GoalResponseOutput;
import com.application.pennypal.application.port.GoalRepositoryPort;
import com.application.pennypal.application.port.TransactionRepositoryPort;
import com.application.pennypal.application.usecases.goal.AddGoal;
import com.application.pennypal.domain.entity.Goal;
import com.application.pennypal.domain.entity.Transaction;
import com.application.pennypal.domain.valueObject.TransactionType;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class AddGoalService implements AddGoal {
    private final GoalApplicationMapper goalApplicationMapper;
    private final GoalRepositoryPort goalRepositoryPort;
    private final TransactionRepositoryPort transactionRepositoryPort;
    @Override
    public GoalResponseOutput execute(AddGoalInputModel goalInputModel,Long userId) {

        /// Map input response data to domain goal entity
        Goal goal = goalApplicationMapper.toDomain(goalInputModel,userId);
        Goal newIncome = goalRepositoryPort.save(goal);

        /// Get contribution transaction list
        List<Transaction> contributionTrx = transactionRepositoryPort.findAllByUserIdAndOriginIdAndType(userId,goal.getId(), TransactionType.GOAL);
        return goalApplicationMapper.toOutput(newIncome,contributionTrx);
    }
}
