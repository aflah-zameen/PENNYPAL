package com.application.pennypal.application.port.in.lend;

import com.application.pennypal.application.dto.output.lend.LoanOutputModel;
import com.application.pennypal.domain.lend.LendingRequest;

import java.util.List;

public interface GetLoansToRepay {
    List<LoanOutputModel> execute(String userId);
}
