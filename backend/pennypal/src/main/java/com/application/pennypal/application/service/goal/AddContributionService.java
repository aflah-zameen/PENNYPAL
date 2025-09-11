package com.application.pennypal.application.service.goal;

import com.application.pennypal.application.dto.input.goal.AddContributionInputModel;
import com.application.pennypal.application.dto.output.goal.GoalContributionOutput;
import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.mappers.goal.GoalContributionApplicationMapper;
import com.application.pennypal.application.port.in.coin.CoinReward;
import com.application.pennypal.application.port.out.repository.CardRepositoryPort;
import com.application.pennypal.application.port.out.repository.GoalContributionRepositoryPort;
import com.application.pennypal.application.port.out.repository.GoalRepositoryPort;
import com.application.pennypal.application.port.out.repository.TransactionRepositoryPort;
import com.application.pennypal.application.port.in.goal.AddContribution;
import com.application.pennypal.domain.card.entity.Card;
import com.application.pennypal.domain.goal.entity.Goal;
import com.application.pennypal.domain.goal.entity.GoalContribution;
import com.application.pennypal.domain.transaction.entity.Transaction;
import com.application.pennypal.domain.valueObject.GoalStatus;
import com.application.pennypal.domain.valueObject.PaymentMethod;
import com.application.pennypal.domain.valueObject.TransactionType;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
public class AddContributionService implements AddContribution {
    private final GoalRepositoryPort goalRepositoryPort;
    private final TransactionRepositoryPort transactionRepositoryPort;
    private final GoalContributionRepositoryPort goalContributionRepositoryPort;
    private final GoalContributionApplicationMapper goalContributionApplicationMapper;
    private final CardRepositoryPort cardRepositoryPort;
    private final CoinReward coinReward;

    @Override
    public GoalContributionOutput execute(String userId, AddContributionInputModel inputModel) {
        Goal goal = goalRepositoryPort.getGoalById(inputModel.goalId())
                .orElseThrow(() -> new ApplicationBusinessException("Goal cannot be found","NOT_FOUND"));
        if(goal.getUserId().equals(userId)){

            Card card = cardRepositoryPort.findByUserIdAndCardId(userId,inputModel.cardId())
                            .orElseThrow(() -> new ApplicationBusinessException("Card cannot be found","NOT_FOUND"));

            /// debit amount from  the card
            card = card.debitAmount(inputModel.amount());
            /// Contribute to goal
            goal.contribute(inputModel.amount());

            cardRepositoryPort.update(card);
            goal = goalRepositoryPort.update(goal,goal.getGoalId());


            /// Create a new Transaction
            Transaction contributionTrx = Transaction.create(
                  inputModel.userId(),
                    goal.getCategoryId(),
                    inputModel.cardId(),
                    null,
                    inputModel.amount(),
                    TransactionType.GOAL,
                    "Goal Contribution",
                    inputModel.notes(),
                    PaymentMethod.CARD,
                    LocalDate.now(),
                    false,
                    null,
                    null,
                    null,
                    null
            );
            Transaction newTransaction = transactionRepositoryPort.save(contributionTrx);

            /// Create new contribution entity
            GoalContribution contribution = GoalContribution.create(
                    inputModel.userId(),
                    inputModel.goalId(),
                    inputModel.cardId(),
                    newTransaction.getTransactionId(),
                    inputModel.amount(),
                    inputModel.notes()
            );

            GoalContribution goalContribution = goalContributionRepositoryPort.save(contribution);

            /// Coin management
            if(goal.getStatus().equals(GoalStatus.COMPLETED) && goal.getEndDate().isAfter(LocalDate.now())){
                BigDecimal coins = coinReward.addCoinsForGoal(goal.getUserId(),goal.getGoalId());
                return goalContributionApplicationMapper.toOutput(goalContribution,coins);
            }

            return goalContributionApplicationMapper.toOutput(goalContribution,null);

        }
        else{
            throw new ApplicationBusinessException("User is not authenticated for this action","UNAUTHORIZED_ACTION");
        }
    }
}
