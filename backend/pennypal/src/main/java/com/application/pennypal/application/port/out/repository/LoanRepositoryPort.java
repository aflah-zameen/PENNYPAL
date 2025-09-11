package com.application.pennypal.application.port.out.repository;

import com.application.pennypal.application.dto.output.lend.LoanAdminSummary;
import com.application.pennypal.application.dto.output.lend.LoanOutputModel;
import com.application.pennypal.application.port.in.lent.LoanFilterInputModel;
import com.application.pennypal.domain.lend.LendingRequest;
import com.application.pennypal.domain.lend.Loan;
import com.application.pennypal.domain.lend.LoanStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface LoanRepositoryPort {
    Loan save(Loan loan);
    Loan update(Loan loan);
    Optional<Loan> getLoan(String loanId);
    BigDecimal getTotalAmountLent(String userId);

    BigDecimal getTotalAmountBorrowed(String userId);
    long getActiveLoanSentCount(String userId);

    long getActiveLoanReceived(String userId);

    long getOverdueLoanCount(String userId);

    List<Loan> getLoansToRepay(String userId);

    List<Loan> getLoansToCollect(String userId);

    List<Loan> getAll();

    List<Loan> getFilteredAll(LoanFilterInputModel inputModel);

    LoanAdminSummary getLoanAdminSummary();
}
