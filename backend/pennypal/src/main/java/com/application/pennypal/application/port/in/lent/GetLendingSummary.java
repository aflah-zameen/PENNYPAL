package com.application.pennypal.application.port.in.lend;

import com.application.pennypal.application.dto.output.lend.LendingSummaryOutputModel;

public interface GetLendingSummary {
    LendingSummaryOutputModel execute(String userId);
}
