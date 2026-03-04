package com.galitianu.casestudy.employee.api;

import com.galitianu.casestudy.base.api.BaseResource;
import com.galitianu.casestudy.employee.mapper.EmployeeMapper;
import com.galitianu.casestudy.employee.service.EmployeeService;
import com.galitianu.casestudy.employee.service.model.Employee;
import com.galitianu.casestudy.openapi.api.EmployeeApi;
import com.galitianu.casestudy.openapi.model.EmployeeRequest;
import com.galitianu.casestudy.openapi.model.EmployeeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class EmployeeResource extends BaseResource implements EmployeeApi {
    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    @Override
    public ResponseEntity<EmployeeResponse> createEmployee(EmployeeRequest employeeRequest) {
        Employee saved = employeeService.save(employeeMapper.mapToModel(employeeRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeMapper.mapToDto(saved));
    }

    @Override
    public ResponseEntity<Void> deleteEmployee(String employeeId) {
        employeeService.deleteById(parseEmployeeId(employeeId));
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<EmployeeResponse> getEmployeeById(String employeeId) {
        Employee employee = employeeService.getRequired(parseEmployeeId(employeeId));
        return ResponseEntity.ok(employeeMapper.mapToDto(employee));
    }

    @Override
    public ResponseEntity<List<EmployeeResponse>> getEmployees() {
        List<EmployeeResponse> employees = employeeService.findAll().stream()
            .map(employeeMapper::mapToDto)
            .toList();

        return ResponseEntity.ok(employees);
    }

    @Override
    public ResponseEntity<EmployeeResponse> updateEmployee(String employeeId, EmployeeRequest employeeRequest) {
        Employee updated = employeeService.update(parseEmployeeId(employeeId), employeeMapper.mapToModel(employeeRequest));
        return ResponseEntity.ok(employeeMapper.mapToDto(updated));
    }

    private UUID parseEmployeeId(String employeeId) {
        try {
            return UUID.fromString(employeeId);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("employeeId must be a valid UUID");
        }
    }

}
