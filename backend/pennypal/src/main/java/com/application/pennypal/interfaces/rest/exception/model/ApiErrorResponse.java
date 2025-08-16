package com.application.pennypal.interfaces.rest.exception.model;

import java.time.LocalDateTime;
import java.util.List;

public record ApiErrorResponse(
        String errorCode,
        String message,
        LocalDateTime timestamp,
        String path,
        List<String> errors
) {}
