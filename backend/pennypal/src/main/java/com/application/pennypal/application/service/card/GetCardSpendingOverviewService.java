package com.application.pennypal.application.service.card;

import com.application.pennypal.application.dto.input.card.CardSpendingInputModel;
import com.application.pennypal.application.dto.output.card.CardSpendingOutputModel;
import com.application.pennypal.application.exception.base.ApplicationValidationException;
import com.application.pennypal.application.port.in.card.GetCardSpendingOverview;
import com.application.pennypal.application.port.out.repository.TransactionRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetCardSpendingOverviewService implements GetCardSpendingOverview {
    private final TransactionRepositoryPort transactionRepositoryPort;
    @Override
    public CardSpendingOutputModel execute(CardSpendingInputModel inputModel) {
        if(inputModel.filter().equalsIgnoreCase("MONTHLY")){
            return transactionRepositoryPort.findMonthlySpending(inputModel.cardId());
        }else if(inputModel.filter().equalsIgnoreCase("WEEKLY")){
            return transactionRepositoryPort.findWeeklySpending(inputModel.cardId());
        }else if(inputModel.filter().equalsIgnoreCase("YEARLY")){
            return transactionRepositoryPort.findYearlySpending(inputModel.cardId());
        }else{
            throw new ApplicationValidationException("Filter value is not valid","NOT_VALID");
        }

    }
}
