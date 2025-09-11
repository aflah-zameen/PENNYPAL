package com.application.pennypal.domain.lend;

public enum LoanStatus {
    ACTIVE,        // Loan is accepted and ongoing
    PARTIAL,       // Loan is partially repaid
    PAID,          // Loan is fully repaid
    OVERDUE,       // Loan has passed its deadline without full repayment
    DISPUTED,      // Lender has filed a case due to non-payment or conflict
    CANCELLED,     // Loan was invalidated or withdrawn before activation
    SETTLED,       // Used if disputes are resolved and payment is completed.
    FORGIVEN       // If the lender waives repayment.
}

