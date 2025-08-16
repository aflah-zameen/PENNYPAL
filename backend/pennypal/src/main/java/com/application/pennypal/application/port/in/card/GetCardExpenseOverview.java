package com.application.pennypal.application.port.in.card;

import com.application.pennypal.application.dto.input.card.ExpenseFilterInputModel;
import com.application.pennypal.application.dto.output.card.CardExpenseOverviewOutputModel;

import java.util.List;

public interface GetCardExpenseOverview {
    List<CardExpenseOverviewOutputModel> execute(String cardId,ExpenseFilterInputModel inputModel);
}
