package com.application.pennypal.application.port.in.lent;

import com.application.pennypal.application.dto.output.lend.LoanOutputModel;

import java.util.List;

public interface GetAllLoans {
    List<LoanOutputModel> execute();
    List<LoanOutputModel> getFiltered(LoanFilterInputModel inputModel);
}
