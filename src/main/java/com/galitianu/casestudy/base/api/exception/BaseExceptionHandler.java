package com.galitianu.casestudy.base.api.exception;

import com.galitianu.casestudy.openapi.model.ErrorResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class BaseExceptionHandler extends ResponseEntityExceptionHandler {

    protected ResponseEntity<Object> handleException(String code, Exception exception, WebRequest request, HttpStatus status) {
        return handleError(code, exception.getMessage(), exception, request, status);
    }

    protected ResponseEntity<Object> handleError(
        String code,
        String message,
        Exception exception,
        WebRequest request,
        HttpStatus status
    ) {
        HttpHeaders headers = new HttpHeaders();
        ErrorResponse body = error(code, message);
        return handleExceptionInternal(exception, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            @NonNull Exception ex,
            Object body,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode statusCode,
            @NonNull WebRequest request
    ) {
        if (body == null && !statusCode.is5xxServerError()) {
            body = error("REQUEST_ERROR", ex.getMessage());
        }

        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    protected ErrorResponse error(String code, String message) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(code);
        errorResponse.setMessage(message);
        return errorResponse;
    }
}
