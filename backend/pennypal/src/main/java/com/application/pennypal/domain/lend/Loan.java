package com.application.pennypal.domain.lend;

import com.application.pennypal.domain.shared.exception.LargeAmountThanExpectedDomainException;
import com.application.pennypal.domain.shared.exception.NegativeAmountNotAllowedExceptionDomain;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Loan {
    private final String loanId;
    private final String requestId;
    private final BigDecimal amount;
    private BigDecimal amountPaid;
    private final LocalDateTime deadline;
    private final LocalDateTime acceptedAt;
    private LoanStatus status;
    private LocalDateTime lastReminderSentAt;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    /// factory static methods
    public static Loan create(
        String requestId,
        BigDecimal amount,
        LocalDateTime deadline
    ){
        String id = "LOAN_"+ UUID.randomUUID();
        return new Loan(
                id,
                requestId,
                amount,
                BigDecimal.ZERO,
                deadline,
                LocalDateTime.now(),
                LoanStatus.ACTIVE,
                null,
                null,
                null
        );


    }

    /// create from the lending request
    public static Loan createFrom(LendingRequest lendingRequest){
        return Loan.create(
                lendingRequest.getRequestId(),
                lendingRequest.getAmount(),
                lendingRequest.getAcceptedDeadline()
        );
    }

    public static Loan reconstruct(
            String loanId,
            String requestId,
            BigDecimal amount,
            BigDecimal amountPaid,
            LocalDateTime deadline,
            LocalDateTime acceptedAt,
            LoanStatus loanStatus,
            LocalDateTime lastReminderSentAt,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ){
        return new Loan(
                loanId,
                requestId,
                amount,
                amountPaid,
                deadline,
                acceptedAt,
                loanStatus,
                lastReminderSentAt,
                createdAt,
                updatedAt
        );

    }

    /// Business methods
    public Loan recordPayment(BigDecimal payment){
        if(payment.compareTo(BigDecimal.ZERO) < 0){
            throw new NegativeAmountNotAllowedExceptionDomain();
        }
        if(payment.compareTo(this.amount.subtract(this.amountPaid)) > 0 ){
            throw new LargeAmountThanExpectedDomainException("The amount is larger than expected");
        }
        this.amountPaid = this.amountPaid.add(payment);

        if(this.amountPaid.compareTo(this.amount) == 0){
            this.status = LoanStatus.PAID;
        }
        if(this.amountPaid.compareTo(this.amount) < 0){
            this.status = LoanStatus.PARTIAL;
        }

        return this;
    }

    public boolean isOverdue(LocalDateTime now){
        return now.isAfter(deadline) && !this.status.equals(LoanStatus.PAID);
    }

    public Loan markReminded(){
        this.lastReminderSentAt = LocalDateTime.now();
        return this;
    }

    public Loan markCaseFiled(){
        this.status = LoanStatus.DISPUTED;
        return this;
    }

}
