package com.application.pennypal.application.port.in.lent;

import com.application.pennypal.application.dto.input.lend.LoanCaseInputModel;
import com.application.pennypal.application.dto.output.lend.LoanOutputModel;

public interface FileLoanCase {
    LoanOutputModel execute(String userId, LoanCaseInputModel inputModel);
}
