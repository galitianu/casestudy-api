package com.galitianu.casestudy.employee.api.exception;

import com.galitianu.casestudy.base.api.exception.BaseExceptionHandler;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE - 3)
public class EmployeeDataIntegrityViolationExceptionHandler extends BaseExceptionHandler {

    private static final String PERSONNEL_NUMBER_UNIQUE_CONSTRAINT = "uk_employee_personnel_number";
    private static final String DUPLICATE_PERSONNEL_NUMBER_MESSAGE = "personnelNumber already exists";
    private static final String GENERIC_DATA_INTEGRITY_MESSAGE = "Request violates a data integrity constraint";

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolationException(
        DataIntegrityViolationException ex,
        WebRequest request
    ) {
        String message = isPersonnelNumberDuplicate(ex)
            ? DUPLICATE_PERSONNEL_NUMBER_MESSAGE
            : GENERIC_DATA_INTEGRITY_MESSAGE;

        return handleError("VALIDATION_ERROR", message, ex, request, HttpStatus.BAD_REQUEST);
    }

    private boolean isPersonnelNumberDuplicate(Throwable throwable) {
        Throwable current = throwable;

        while (current != null) {
            if (current instanceof ConstraintViolationException constraintViolationException
                && PERSONNEL_NUMBER_UNIQUE_CONSTRAINT.equalsIgnoreCase(constraintViolationException.getConstraintName())) {
                return true;
            }

            if (current.getMessage() != null && current.getMessage().contains(PERSONNEL_NUMBER_UNIQUE_CONSTRAINT)) {
                return true;
            }

            current = current.getCause();
        }

        return false;
    }
}
