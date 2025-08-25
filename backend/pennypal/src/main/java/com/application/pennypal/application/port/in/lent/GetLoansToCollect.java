package com.application.pennypal.application.port.in.lend;

import com.application.pennypal.application.dto.output.lend.LoanOutputModel;

import java.util.List;

public interface GetLoansToCollect {
    List<LoanOutputModel> execute(String userId);
}
