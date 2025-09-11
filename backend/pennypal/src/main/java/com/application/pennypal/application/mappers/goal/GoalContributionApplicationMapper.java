package com.application.pennypal.application.mappers.goal;

import com.application.pennypal.application.dto.output.goal.GoalContributionOutput;
import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.port.out.repository.CardRepositoryPort;
import com.application.pennypal.domain.card.entity.Card;
import com.application.pennypal.domain.goal.entity.GoalContribution;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class GoalContributionApplicationMapper {
    private final CardRepositoryPort cardRepositoryPort;
    public GoalContributionOutput toOutput(GoalContribution goalContribution, BigDecimal coins){
        Card card = goalContribution.getCardId() != null ?
                cardRepositoryPort.findByCardId(goalContribution.getGoalId())
                .orElseThrow(()-> new ApplicationBusinessException("Card entity not found","NOT_FOUND")) :
                null;
        return new GoalContributionOutput(
                goalContribution.getContributionId(),
                card == null ? null : card.getCardNumber(),
                goalContribution.getAmount(),
                goalContribution.getDate(),
                goalContribution.getNotes(),
                coins
        );
    }
}
