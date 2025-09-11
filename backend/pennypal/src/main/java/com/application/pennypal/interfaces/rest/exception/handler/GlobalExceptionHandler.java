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


@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private ResponseEntity<ApiErrorResponse> buildResponse(
            String errorCode,
            String message,
            HttpStatus status,
            HttpServletRequest request,
            List<String> errors
    ) {
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

    // --- Domain Layer ---
    @ExceptionHandler(DomainValidationException.class)
    public ResponseEntity<ApiErrorResponse> handleDomainValidation(DomainValidationException ex, HttpServletRequest request) {
        return buildResponse(ex.code().value(), ex.getMessage(), HttpStatus.BAD_REQUEST, request,null);
    }

    @ExceptionHandler(DomainBusinessException.class)
    public ResponseEntity<ApiErrorResponse> handleDomainBusiness(DomainBusinessException ex, HttpServletRequest request) {
        return buildResponse(ex.code().value(), ex.getMessage(), HttpStatus.CONFLICT, request,null);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ApiErrorResponse> handleGenericDomain(DomainException ex, HttpServletRequest request) {
        return buildResponse(ex.code().value(), ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY, request,null);
    }

    // --- Application Layer ---
    @ExceptionHandler(ApplicationValidationException.class)
    public ResponseEntity<ApiErrorResponse> handleAppValidation(ApplicationValidationException ex, HttpServletRequest request) {
        return buildResponse(ex.getErrorCode(), ex.getMessage(), HttpStatus.BAD_REQUEST, request,null);
    }

    @ExceptionHandler(ApplicationBusinessException.class)
    public ResponseEntity<ApiErrorResponse> handleAppBusiness(ApplicationBusinessException ex, HttpServletRequest request) {
        return buildResponse(ex.getErrorCode(), ex.getMessage(), HttpStatus.CONFLICT, request,null);
    }

    @ExceptionHandler(UserSuspendedApplicationException.class)
    public ResponseEntity<ApiErrorResponse> handleSuspensionException(UserSuspendedApplicationException ex , HttpServletRequest servletRequest){
        return buildResponse(ex.getErrorCode(),ex.getMessage(),HttpStatus.FORBIDDEN,servletRequest,null);
    }

//    @ExceptionHandler(ApplicationException.class)
//    public ResponseEntity<ApiErrorResponse> handleAppGeneric(ApplicationException ex, HttpServletRequest request) {
//        return buildResponse(ex.getErrorCode(), ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, request,null);
//    }

    // --- Infrastructure Layer ---
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<ApiErrorResponse> handleDatabase(DatabaseException ex, HttpServletRequest request) {
        return buildResponse(ex.getErrorCode(), ex.getMessage(), HttpStatus.SERVICE_UNAVAILABLE, request,null);
    }

//    @ExceptionHandler(ExternalServiceException.class)
//    public ResponseEntity<ApiError> handleExternalService(ExternalServiceException ex, HttpServletRequest request) {
//        return buildResponse(ex.getErrorCode(), ex.getMessage(), HttpStatus.GATEWAY_TIMEOUT, request);
//    }

    @ExceptionHandler(InfrastructureException.class)
    public ResponseEntity<ApiErrorResponse> handleInfraGeneric(InfrastructureException ex, HttpServletRequest request) {
        return buildResponse(ex.getErrorCode(), ex.getMessage(), HttpStatus.SERVICE_UNAVAILABLE, request,null);
    }

    // --- Interface Layer (Web/API) ---

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(MethodArgumentNotValidException ex,HttpServletRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        return buildResponse(InterfaceErrorCode.METHOD_ARGUMENT_NOT_VALID.getValue(), "The request method arguments are not valid",HttpStatus.BAD_REQUEST,request,errors);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidRequest(InvalidRequestException ex, HttpServletRequest request) {
        return buildResponse(ex.getErrorCode(), ex.getMessage(), HttpStatus.BAD_REQUEST, request,null);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ApiErrorResponse> handleUnauthorized(UnauthorizedAccessException ex, HttpServletRequest request) {
        return buildResponse(ex.getErrorCode(), ex.getMessage(), HttpStatus.UNAUTHORIZED, request,null);
    }

    @ExceptionHandler(ForbiddenAccessException.class)
    public ResponseEntity<ApiErrorResponse> handleUnauthorized(ForbiddenAccessException ex, HttpServletRequest request) {
        return buildResponse(ex.getErrorCode(), ex.getMessage(), HttpStatus.FORBIDDEN, request,null);
    }


    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiErrorResponse> handleMaxUpload(MaxUploadSizeExceededException ex,HttpServletRequest request){
        return buildResponse(InterfaceErrorCode.MAX_SIZE_EXCEED.getValue(), ex.getMessage(),HttpStatus.BAD_REQUEST,request,null );
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNoResourceFound(NoResourceFoundException ex,HttpServletRequest request){
        return buildResponse(InterfaceErrorCode.RESOURCE_NOT_FOUND.getValue(), ex.getMessage(),HttpStatus.NOT_FOUND,request,null );
    }


//    @ExceptionHandler(LockedException.class)
//    public ResponseEntity<ApiErrorResponse> handleLockedException(LockedException ex,HttpServletRequest request){
//        return buildResponse(InterfaceErrorCode.USER_BLOCKED.getValue(),"User is blocked",HttpStatus.FORBIDDEN,request,null );
//    }
//
//    @ExceptionHandler(DisabledException.class)
//    public ResponseEntity<ApiErrorResponse> handleDisabledException(LockedException ex,HttpServletRequest request){
//        return buildResponse(InterfaceErrorCode.USER_NOT_VERIFIED.getValue(),"User is not verified",HttpStatus.FORBIDDEN,request,null );
//    }

    // --- Fallback: Generic ---
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneric(Exception ex, HttpServletRequest request) {
        logger.error("Unhandled exception occurred at [{}]: {}", request.getRequestURI(), ex.getMessage(), ex);
        return buildResponse(InterfaceErrorCode.UNEXPECTED_ERROR.getValue(), "Unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR, request,null);
    }
}

