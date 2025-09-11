package com.application.pennypal.domain.lend;

import com.application.pennypal.domain.shared.exception.DomainErrorCode;
import com.application.pennypal.domain.shared.exception.base.DomainBusinessException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class LendingRequest {
    private final String requestId;
    private final String requestedBy;
    private final String requestedTo;
    private final BigDecimal amount;
    private final String message;
    private final LocalDateTime proposedDeadline;
    private LocalDateTime acceptedDeadline;
    private LendingRequestStatus status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private Loan loan;

    /// factory method to create
    public static LendingRequest create(
            String requestedBy,
            String requestedTo,
            BigDecimal amount,
            String message,
            LocalDateTime proposedDeadline,
            LocalDateTime acceptedDeadline){
        String requestId = "LEND_"+ UUID.randomUUID();
        return new LendingRequest(
                requestId,
                requestedBy,
                requestedTo,
                amount,
                message,
                proposedDeadline,
                acceptedDeadline,
                LendingRequestStatus.PENDING,
                null,
                null,
                null
        );
    }

    /// factory method reconstruct
    public static LendingRequest reconstruct(
            String requestId,
            String requestedBy,
            String requestedTo,
            BigDecimal amount,
            String message,
            LocalDateTime proposedDeadline,
            LocalDateTime acceptedDeadline,
            LendingRequestStatus status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            Loan loan
    ){
        return new LendingRequest(
                requestId,
                requestedBy,
                requestedTo,
                amount,
                message,
                proposedDeadline,
                acceptedDeadline,
                status,
                createdAt,
                updatedAt,
                loan
        );
    }

    /// Business Methods
    public LendingRequest acceptRequest(LocalDateTime deadline){
        if(this.status != LendingRequestStatus.PENDING)
            throw new DomainBusinessException("Only pending requests can be accepted", DomainErrorCode.ILLEGAL_STATE);
        this.acceptedDeadline = deadline;
        this.status = LendingRequestStatus.ACCEPTED;
        this.loan = Loan.createFrom(this);
        return this;
    }

    public LendingRequest rejectRequest(){
        if(this.status != LendingRequestStatus.PENDING)
            throw new DomainBusinessException("Only pending requests can be rejected", DomainErrorCode.ILLEGAL_STATE);
        this.status = LendingRequestStatus.REJECTED;
        return this;
    }

    public LendingRequest setAcceptedDeadline(LocalDateTime acceptedDeadline){
        this.acceptedDeadline= acceptedDeadline;
        return this;
    }

    public LendingRequest cancelRequest(){
        if(this.status != LendingRequestStatus.PENDING)
            throw new DomainBusinessException("Only pending requests can be rejected", DomainErrorCode.ILLEGAL_STATE);
        this.status = LendingRequestStatus.CANCELLED;
        return this;
    }

}
