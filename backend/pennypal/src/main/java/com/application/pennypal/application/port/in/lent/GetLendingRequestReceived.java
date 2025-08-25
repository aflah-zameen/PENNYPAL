package com.application.pennypal.application.port.in.lend;

import com.application.pennypal.application.dto.output.lend.LendingRequestOutputModel;

import java.util.List;

public interface GetLendingRequestReceived {
    List<LendingRequestOutputModel> execute(String userId);
}
