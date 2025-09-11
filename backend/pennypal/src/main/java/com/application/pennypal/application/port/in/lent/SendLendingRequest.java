package com.application.pennypal.application.port.in.lent;

import com.application.pennypal.application.dto.input.lend.LendRequestInputModel;
import com.application.pennypal.application.dto.output.lend.LendingRequestOutputModel;

public interface SendLendingRequest {
    LendingRequestOutputModel execute(String userId, LendRequestInputModel inputModel);
}
