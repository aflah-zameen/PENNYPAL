package com.application.pennypal.application.port.in.card;

import com.application.pennypal.application.dto.input.card.CardSpendingInputModel;
import com.application.pennypal.application.dto.output.card.CardSpendingOutputModel;

public interface GetCardSpendingOverview {
    CardSpendingOutputModel execute(CardSpendingInputModel inputModel);
}
