package com.application.pennypal.application.service.goal;

import com.application.pennypal.application.dto.input.goal.AddGoalInputModel;
import com.application.pennypal.application.mappers.goal.GoalApplicationMapper;
import com.application.pennypal.application.dto.output.goal.GoalResponseOutput;
import com.application.pennypal.application.port.out.repository.GoalContributionRepositoryPort;
import com.application.pennypal.application.port.out.repository.GoalRepositoryPort;
import com.application.pennypal.application.port.out.repository.TransactionRepositoryPort;
import com.application.pennypal.application.port.in.goal.AddGoal;
import com.application.pennypal.domain.goal.entity.Goal;
import com.application.pennypal.domain.transaction.entity.Transaction;
import com.application.pennypal.domain.valueObject.TransactionType;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class AddGoalService implements AddGoal {
    private final GoalApplicationMapper goalApplicationMapper;
    private final GoalRepositoryPort goalRepositoryPort;
    private final TransactionRepositoryPort transactionRepositoryPort;
    @Override
    public GoalResponseOutput execute(AddGoalInputModel goalInputModel,String userId) {

        /// Map input response data to domain goal entity
        Goal goal = goalApplicationMapper.toDomain(goalInputModel,userId);
        Goal newIncome = goalRepositoryPort.save(goal);
        return goalApplicationMapper.toOutput(newIncome,new ArrayList<>());
    }
}
