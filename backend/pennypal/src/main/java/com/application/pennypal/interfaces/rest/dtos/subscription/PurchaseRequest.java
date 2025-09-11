package com.application.pennypal.interfaces.rest.dtos.subscription;

import jakarta.validation.constraints.*;

public record PurchaseRequest(

        @NotNull(message = "Plan ID is required")
        @NotEmpty(message = "Plan Id cannot be empty")
        String planId,
        @NotNull(message = "Session ID is required")
        @NotEmpty(message = "Session Id cannot be empty")
        String sessionId
) {}