package com.galitianu.casestudy.base.api.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class RestUnhandledExceptionHandler extends BaseExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(RestUnhandledExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleUnhandledException(Exception ex, WebRequest request) {
        log.error("Unhandled exception.", ex);
        return handleException("INTERNAL_SERVER_ERROR", ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
