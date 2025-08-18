package com.application.pennypal.domain.lend;

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
            LocalDateTime updatedAt
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
                updatedAt
        );
    }

    /// Business Methods
    public LendingRequest updateStatus(LendingRequestStatus status){
        this.status = status;
        return this;
    }

    public LendingRequest setAcceptedDeadline(LocalDateTime acceptedDeadline){
        this.acceptedDeadline= acceptedDeadline;
        return this;
    }
}
