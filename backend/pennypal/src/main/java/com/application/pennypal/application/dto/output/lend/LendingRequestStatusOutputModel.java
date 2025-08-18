package com.application.pennypal.application.dto.output.lend;

import com.application.pennypal.domain.lend.LendingRequestStatus;

import java.time.LocalDateTime;

public record LendingRequestStatusOutputModel(
        LendingRequestStatus status,
        LocalDateTime acceptedDeadline
) {
}
