package com.application.pennypal.application.port.in.lend;

import com.application.pennypal.application.dto.output.lend.LendingRequestOutputModel;

import java.util.List;

public interface GetLendingRequestsSent {
    List<LendingRequestOutputModel> execute (String userId);
}
