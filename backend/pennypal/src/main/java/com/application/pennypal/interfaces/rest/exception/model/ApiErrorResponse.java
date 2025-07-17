package com.application.pennypal.interfaces.rest.exception.model;

import java.time.LocalDateTime;

public record ApiErrorResponse(
        String errorCode,
        String message,
        LocalDateTime timestamp,
        String path
) {}
