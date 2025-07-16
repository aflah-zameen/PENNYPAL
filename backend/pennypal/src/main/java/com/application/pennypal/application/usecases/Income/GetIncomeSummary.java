package com.application.pennypal.application.usecases.Income;

import com.application.pennypal.application.output.income.IncomeSummaryOutput;

public interface GetIncomeSummary {
    IncomeSummaryOutput execute(Long userId);
}
