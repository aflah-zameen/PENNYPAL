package com.application.pennypal.application.usecases.Income;

import com.application.pennypal.application.output.income.AllPendingIncomeSummaryOutput;

public interface GetAllPendingIncomeSummary {
    AllPendingIncomeSummaryOutput execute(Long userId);
}
