package com.application.pennypal.interfaces.rest.exception.handler;

import com.application.pennypal.application.exception.base.ApplicationBusinessException;
import com.application.pennypal.application.exception.base.ApplicationValidationException;
import com.application.pennypal.application.exception.usecase.auth.UserSuspendedApplicationException;
import com.application.pennypal.domain.shared.exception.base.DomainBusinessException;
import com.application.pennypal.domain.shared.exception.base.DomainException;
import com.application.pennypal.domain.shared.exception.base.DomainValidationException;
import com.application.pennypal.infrastructure.exception.base.DatabaseException;
import com.application.pennypal.infrastructure.exception.base.InfrastructureException;
import com.application.pennypal.interfaces.rest.exception.InterfaceErrorCode;
import com.application.pennypal.interfaces.rest.exception.auth.ForbiddenAccessException;
import com.application.pennypal.interfaces.rest.exception.auth.UnauthorizedAccessException;
import com.application.pennypal.interfaces.rest.exception.model.ApiErrorResponse;
import com.application.pennypal.interfaces.rest.exception.validation.InvalidRequestException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.List;

///  logger imports
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.resource.NoResourceFoundException;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ApiErrorResponse> buildResponse(
            String errorCode,
            String message,
            HttpStatus status,
            HttpServletRequest request,
            List<String> errors
    ) {
        // Log error consistently
        if (status.is5xxServerError()) {
            log.error("Error [{}] at [{}]: {} | Details: {}", errorCode, request.getRequestURI(), message, errors, new Throwable());
        } else if (status.is4xxClientError()) {
            log.warn("Client error [{}] at [{}]: {} | Details: {}", errorCode, request.getRequestURI(), message, errors);
        } else {
            log.info("Handled exception [{}] at [{}]: {} | Details: {}", errorCode, request.getRequestURI(), message, errors);
        }

        return ResponseEntity
                .status(status)
                .body(new ApiErrorResponse(
                        errorCode,
                        message,
                        LocalDateTime.now(),
                        request.getRequestURI(),
                        errors
                ));
    }

    // ---------------- Domain Layer Exceptions ----------------
    @ExceptionHandler(DomainValidationException.class)
    public ResponseEntity<ApiErrorResponse> handleDomainValidation(DomainValidationException ex, HttpServletRequest request) {
        return buildResponse(ex.code().value(), ex.getMessage(), HttpStatus.BAD_REQUEST, request, null);
    }

    @ExceptionHandler(DomainBusinessException.class)
    public ResponseEntity<ApiErrorResponse> handleDomainBusiness(DomainBusinessException ex, HttpServletRequest request) {
        return buildResponse(ex.code().value(), ex.getMessage(), HttpStatus.CONFLICT, request, null);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ApiErrorResponse> handleGenericDomain(DomainException ex, HttpServletRequest request) {
        return buildResponse(ex.code().value(), ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY, request, null);
    }

    // ---------------- Application Layer Exceptions ----------------
    @ExceptionHandler(ApplicationValidationException.class)
    public ResponseEntity<ApiErrorResponse> handleAppValidation(ApplicationValidationException ex, HttpServletRequest request) {
        return buildResponse(ex.getErrorCode(), ex.getMessage(), HttpStatus.BAD_REQUEST, request, null);
    }

    @ExceptionHandler(ApplicationBusinessException.class)
    public ResponseEntity<ApiErrorResponse> handleAppBusiness(ApplicationBusinessException ex, HttpServletRequest request) {
        return buildResponse(ex.getErrorCode(), ex.getMessage(), HttpStatus.CONFLICT, request, null);
    }

    @ExceptionHandler(UserSuspendedApplicationException.class)
    public ResponseEntity<ApiErrorResponse> handleSuspended(UserSuspendedApplicationException ex, HttpServletRequest request) {
        return buildResponse(ex.getErrorCode(), ex.getMessage(), HttpStatus.FORBIDDEN, request, null);
    }

    // ---------------- Infrastructure Layer Exceptions ----------------
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<ApiErrorResponse> handleDatabase(DatabaseException ex, HttpServletRequest request) {
        return buildResponse(ex.getErrorCode(), ex.getMessage(), HttpStatus.SERVICE_UNAVAILABLE, request, null);
    }

    @ExceptionHandler(InfrastructureException.class)
    public ResponseEntity<ApiErrorResponse> handleInfraGeneric(InfrastructureException ex, HttpServletRequest request) {
        return buildResponse(ex.getErrorCode(), ex.getMessage(), HttpStatus.SERVICE_UNAVAILABLE, request, null);
    }

    // ---------------- Interface Layer Exceptions ----------------
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        return buildResponse(InterfaceErrorCode.METHOD_ARGUMENT_NOT_VALID.getValue(),
                "Request arguments are invalid", HttpStatus.BAD_REQUEST, request, errors);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidRequest(InvalidRequestException ex, HttpServletRequest request) {
        return buildResponse(ex.getErrorCode(), ex.getMessage(), HttpStatus.BAD_REQUEST, request, null);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ApiErrorResponse> handleUnauthorized(UnauthorizedAccessException ex, HttpServletRequest request) {
        return buildResponse(ex.getErrorCode(), ex.getMessage(), HttpStatus.UNAUTHORIZED, request, null);
    }

    @ExceptionHandler(ForbiddenAccessException.class)
    public ResponseEntity<ApiErrorResponse> handleForbidden(ForbiddenAccessException ex, HttpServletRequest request) {
        return buildResponse(ex.getErrorCode(), ex.getMessage(), HttpStatus.FORBIDDEN, request, null);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiErrorResponse> handleMaxUpload(MaxUploadSizeExceededException ex, HttpServletRequest request) {
        return buildResponse(InterfaceErrorCode.MAX_SIZE_EXCEED.getValue(), ex.getMessage(), HttpStatus.BAD_REQUEST, request, null);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNoResourceFound(NoResourceFoundException ex, HttpServletRequest request) {
        return buildResponse(InterfaceErrorCode.RESOURCE_NOT_FOUND.getValue(), ex.getMessage(), HttpStatus.NOT_FOUND, request, null);
    }

    // ---------------- Fallback Generic ----------------
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneric(Exception ex, HttpServletRequest request) {
        // Centralized logging for unhandled exceptions
        log.error("Unhandled exception at [{}]: {}", request.getRequestURI(), ex.getMessage(), ex);
        return buildResponse(InterfaceErrorCode.UNEXPECTED_ERROR.getValue(), "Unexpected error occurred",
                HttpStatus.INTERNAL_SERVER_ERROR, request, null);
    }
}