package com.galitianu.casestudy.employee.service;

import java.util.UUID;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(UUID employeeId) {
        super("Employee not found: " + employeeId);
    }
}
