package com.application.pennypal.application.service.card;

import com.application.pennypal.application.dto.input.card.ExpenseFilterInputModel;
import com.application.pennypal.application.dto.output.card.CardExpenseOverviewOutputModel;
import com.application.pennypal.application.exception.base.ApplicationValidationException;
import com.application.pennypal.application.port.in.card.GetCardExpenseOverview;
import com.application.pennypal.application.port.out.repository.TransactionRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class GetCardExpenseOverviewService implements GetCardExpenseOverview {
    private final TransactionRepositoryPort transactionRepositoryPort;
    @Override
    public List<CardExpenseOverviewOutputModel> execute(String cardId,ExpenseFilterInputModel inputModel) {
        LocalDate startDate;
        LocalDate endDate = LocalDate.now();

        switch (inputModel.range().toLowerCase()) {
            case "daily":
                startDate = endDate;
                break;
            case "weekly":
                startDate = endDate.minusDays(6);
                break;
            case "monthly":
                startDate = endDate.withDayOfMonth(1);
                break;
            case "custom":
                startDate = inputModel.fromDate();
                endDate = inputModel.toDate();
                break;
            default:
                throw new ApplicationValidationException("Invalid range: " + inputModel.range(),"INVALID_RANGE");
        }

        return transactionRepositoryPort.calculateCardExpenseOverview(cardId,startDate,endDate);
    }
}
