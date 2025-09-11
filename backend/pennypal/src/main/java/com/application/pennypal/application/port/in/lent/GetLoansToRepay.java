package com.application.pennypal.application.port.in.lent;

import com.application.pennypal.application.dto.output.lend.LoanOutputModel;

import java.util.List;

public interface GetLoansToRepay {
    List<LoanOutputModel> execute(String userId);
}
