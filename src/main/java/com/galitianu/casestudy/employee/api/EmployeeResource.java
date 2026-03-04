package com.galitianu.casestudy.employee.api;

import com.galitianu.casestudy.base.api.BaseResource;
import com.galitianu.casestudy.employee.mapper.EmployeeMapper;
import com.galitianu.casestudy.employee.service.EmployeeService;
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
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(employeeMapper.mapToResponse(employeeService.save(employeeMapper.mapRequestToModel(employeeRequest))));
    }

    @Override
    public ResponseEntity<Void> deleteEmployee(String employeeId) {
        employeeService.deleteById(parseEmployeeId(employeeId));
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<EmployeeResponse> getEmployeeById(String employeeId) {
        return ResponseEntity.ok(employeeMapper.mapToResponse(employeeService.getRequired(parseEmployeeId(employeeId))));
    }

    @Override
    public ResponseEntity<List<EmployeeResponse>> getEmployees() {
        return ResponseEntity.ok(employeeMapper.mapToResponses(employeeService.findAll()));
    }

    @Override
    public ResponseEntity<EmployeeResponse> updateEmployee(String employeeId, EmployeeRequest employeeRequest) {
        return ResponseEntity.ok(
            employeeMapper.mapToResponse(
                employeeService.update(parseEmployeeId(employeeId), employeeMapper.mapRequestToModel(employeeRequest))
            )
        );
    }

    private UUID parseEmployeeId(String employeeId) {
        try {
            return UUID.fromString(employeeId);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("employeeId must be a valid UUID");
        }
    }

}
