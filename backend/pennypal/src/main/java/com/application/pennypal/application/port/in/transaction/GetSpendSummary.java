package com.application.pennypal.application.port.in.transaction;

import com.application.pennypal.application.dto.output.transaction.SpendSummaryOutputModel;

public interface GetSpendSummary {
    SpendSummaryOutputModel execute(String userId);
}
