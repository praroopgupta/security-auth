package com.pg.securityauth.exception;

import com.pg.securityauth.model.ErrorResponse;
import com.pg.securityauth.util.Errors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    @ExceptionHandler(value = { BadCredentialsException.class, AccessDeniedException.class })
    protected ResponseEntity<ErrorResponse> handleAuthFailException(Exception ex, WebRequest request) {
        LOGGER.error(ex.toString());
        ErrorResponse errorResponse = new ErrorResponse(Errors.ESA_401.getCode(), Errors.ESA_401.getDescription(), LocalDateTime.now());
        return new ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = { SecurityAuthException.class })
    protected ResponseEntity<ErrorResponse> handleSecurityAuthException(SecurityAuthException ex, WebRequest request) {
        LOGGER.error(ex.toString());
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMsg(), LocalDateTime.now());
        return new ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = { UsernameNotFoundException.class })
    protected ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException ex, WebRequest request) {
        LOGGER.error(ex.toString());
        ErrorResponse errorResponse = new ErrorResponse(Errors.ESA_400.getCode(), ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<ErrorResponse> handleAllException(Exception ex, WebRequest request) {
        LOGGER.error(ex.toString());
        ErrorResponse errorResponse = new ErrorResponse(Errors.ESA_999.getCode(), Errors.ESA_999.getDescription(), LocalDateTime.now());
        return new ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
