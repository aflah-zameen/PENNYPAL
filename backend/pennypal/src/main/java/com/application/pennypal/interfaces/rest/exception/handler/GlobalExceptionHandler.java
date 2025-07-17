package com.application.pennypal.interfaces.rest.exception.handler;

import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.exception.base.ApplicationValidationException;
import com.application.pennypal.domain.exception.base.DomainBusinessException;
import com.application.pennypal.domain.exception.base.DomainException;
import com.application.pennypal.domain.exception.base.DomainValidationException;
import com.application.pennypal.infrastructure.exception.base.DatabaseException;
import com.application.pennypal.infrastructure.exception.base.InfrastructureException;
import com.application.pennypal.interfaces.rest.exception.auth.ForbiddenAccessException;
import com.application.pennypal.interfaces.rest.exception.auth.UnauthorizedAccessException;
import com.application.pennypal.interfaces.rest.exception.model.ApiErrorResponse;
import com.application.pennypal.interfaces.rest.exception.validation.InvalidRequestException;
import com.application.pennypal.shared.exception.ApplicationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;

///  logger imports
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private ResponseEntity<ApiErrorResponse> buildResponse(
            String errorCode,
            String message,
            HttpStatus status,
            HttpServletRequest request
    ) {
        return ResponseEntity
                .status(status)
                .body(new ApiErrorResponse(
                        errorCode,
                        message,
                        LocalDateTime.now(),
                        request.getRequestURI()
                ));
    }

    // --- Domain Layer ---
    @ExceptionHandler(DomainValidationException.class)
    public ResponseEntity<ApiErrorResponse> handleDomainValidation(DomainValidationException ex, HttpServletRequest request) {
        return buildResponse(ex.code().value(), ex.getMessage(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(DomainBusinessException.class)
    public ResponseEntity<ApiErrorResponse> handleDomainBusiness(DomainBusinessException ex, HttpServletRequest request) {
        return buildResponse(ex.code().value(), ex.getMessage(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ApiErrorResponse> handleGenericDomain(DomainException ex, HttpServletRequest request) {
        return buildResponse(ex.code().value(), ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    // --- Application Layer ---
    @ExceptionHandler(ApplicationValidationException.class)
    public ResponseEntity<ApiErrorResponse> handleAppValidation(ApplicationValidationException ex, HttpServletRequest request) {
        return buildResponse(ex.getErrorCode(), ex.getMessage(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(ApplicationBusinessException.class)
    public ResponseEntity<ApiErrorResponse> handleAppBusiness(ApplicationBusinessException ex, HttpServletRequest request) {
        return buildResponse(ex.getErrorCode(), ex.getMessage(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ApiErrorResponse> handleAppGeneric(ApplicationException ex, HttpServletRequest request) {
        return buildResponse(ex.getErrorCode(), ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    // --- Infrastructure Layer ---
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<ApiErrorResponse> handleDatabase(DatabaseException ex, HttpServletRequest request) {
        return buildResponse(ex.getErrorCode(), ex.getMessage(), HttpStatus.SERVICE_UNAVAILABLE, request);
    }

//    @ExceptionHandler(ExternalServiceException.class)
//    public ResponseEntity<ApiError> handleExternalService(ExternalServiceException ex, HttpServletRequest request) {
//        return buildResponse(ex.getErrorCode(), ex.getMessage(), HttpStatus.GATEWAY_TIMEOUT, request);
//    }

    @ExceptionHandler(InfrastructureException.class)
    public ResponseEntity<ApiErrorResponse> handleInfraGeneric(InfrastructureException ex, HttpServletRequest request) {
        return buildResponse(ex.getErrorCode(), ex.getMessage(), HttpStatus.SERVICE_UNAVAILABLE, request);
    }

    // --- Interface Layer (Web/API) ---
    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidRequest(InvalidRequestException ex, HttpServletRequest request) {
        return buildResponse(ex.getErrorCode(), ex.getMessage(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ApiErrorResponse> handleUnauthorized(UnauthorizedAccessException ex, HttpServletRequest request) {
        return buildResponse(ex.getErrorCode(), ex.getMessage(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(ForbiddenAccessException.class)
    public ResponseEntity<ApiErrorResponse> handleUnauthorized(ForbiddenAccessException ex, HttpServletRequest request) {
        return buildResponse(ex.getErrorCode(), ex.getMessage(), HttpStatus.FORBIDDEN, request);
    }

    // --- Fallback: Generic ---
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneric(Exception ex, HttpServletRequest request) {
        logger.error("Unhandled exception occurred at [{}]: {}", request.getRequestURI(), ex.getMessage(), ex);
        return buildResponse("GENERIC_ERROR", "Unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}

