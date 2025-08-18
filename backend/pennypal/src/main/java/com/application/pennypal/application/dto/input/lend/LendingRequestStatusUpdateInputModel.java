package com.application.pennypal.application.dto.input.lend;

import com.application.pennypal.domain.lend.LendingRequestStatus;

import java.time.LocalDateTime;

public record LendingRequestStatusUpdateInputModel(
        String requestId,
        LendingRequestStatus status,
        LocalDateTime acceptedDeadline
) {
}
