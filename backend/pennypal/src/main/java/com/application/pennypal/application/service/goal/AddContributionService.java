package com.application.pennypal.application.service.goal;

import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.port.GoalRepositoryPort;
import com.application.pennypal.application.port.TransactionRepositoryPort;
import com.application.pennypal.application.usecases.goal.AddContribution;
import com.application.pennypal.domain.entity.Goal;
import com.application.pennypal.domain.entity.Transaction;
import com.application.pennypal.domain.valueObject.TransactionType;
import com.application.pennypal.domain.valueObject.TransactionStatus;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
public class AddContributionService implements AddContribution {
    private final GoalRepositoryPort goalRepositoryPort;
    private final TransactionRepositoryPort transactionRepositoryPort;
    @Override
    public void execute(Long userId,Long goalId, BigDecimal amount, String notes) {
        Goal goal = goalRepositoryPort.getGoalById(goalId)
                .orElseThrow(() -> new ApplicationBusinessException("Goal cannot be found","NOT_FOUND"));
        if(goal.getUserId().equals(userId)){
            goal.contribute(amount);
            goalRepositoryPort.save(goal);

            /// Create a new Transaction
            Transaction contributionTrx = new Transaction(
                    userId,
                    amount,
                    LocalDate.now(),
                    TransactionType.GOAL,
                    goal.getId(),
                    TransactionStatus.COMPLETED,
                    goal.getCategoryId(),
                    notes,
                    null,
                    false,
                    null,
                    null,
                    null
            );
            transactionRepositoryPort.save(contributionTrx);
        }else{
            throw new ApplicationBusinessException("User is not authenticated for this action","UNAUTHORIZED_ACTION");
        }
    }
}
