package com.galitianu.casestudy.employee.api.exception;

import com.galitianu.casestudy.base.api.exception.BaseExceptionHandler;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE - 1)
public class EmployeeNotFoundExceptionHandler extends BaseExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    protected ResponseEntity<Object> handleEmployeeNotFoundException(EmployeeNotFoundException ex, WebRequest request) {
        return handleException("EMPLOYEE_NOT_FOUND", ex, request, HttpStatus.NOT_FOUND);
    }
}
