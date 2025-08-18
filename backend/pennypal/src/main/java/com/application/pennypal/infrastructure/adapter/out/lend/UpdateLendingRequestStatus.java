package com.application.pennypal.infrastructure.adapter.out.lend;

import com.application.pennypal.application.dto.input.lend.LendingRequestStatusUpdateInputModel;
import com.application.pennypal.application.dto.output.lend.LendingRequestStatusOutputModel;

public interface UpdateLendingRequestStatus {
    LendingRequestStatusOutputModel execute(LendingRequestStatusUpdateInputModel inputModel);
}
