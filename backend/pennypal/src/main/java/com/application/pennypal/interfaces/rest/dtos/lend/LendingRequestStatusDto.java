package com.application.pennypal.interfaces.rest.dtos.lend;

import java.time.LocalDateTime;

public record LendingRequestStatusDto(
        String requestId,
        String status,
        LocalDateTime acceptedDeadline
) {
}
