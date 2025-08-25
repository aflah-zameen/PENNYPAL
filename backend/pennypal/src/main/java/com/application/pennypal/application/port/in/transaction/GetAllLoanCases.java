package com.application.pennypal.application.port.in.transaction;

import com.application.pennypal.application.dto.output.lend.LoanCaseOutputModel;

import java.util.List;

public interface GetAllLoanCases {
    List<LoanCaseOutputModel> execute();
}
