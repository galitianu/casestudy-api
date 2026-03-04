package com.galitianu.casestudy.employee.service;

import com.galitianu.casestudy.base.service.BaseEntityService;
import com.galitianu.casestudy.employee.api.exception.EmployeeNotFoundException;
import com.galitianu.casestudy.employee.mapper.EmployeeMapper;
import com.galitianu.casestudy.employee.persistence.entity.EmployeeEntity;
import com.galitianu.casestudy.employee.persistence.repository.EmployeeRepository;
import com.galitianu.casestudy.employee.service.model.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmployeeService extends BaseEntityService<Employee, EmployeeEntity> {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    protected EmployeeRepository getRepository() {
        return employeeRepository;
    }

    @Override
    protected EmployeeMapper getMapper() {
        return employeeMapper;
    }

    @Transactional
    public Employee update(UUID employeeId, Employee employee) {
        Employee current = findById(employeeId)
            .orElseThrow(() -> new EmployeeNotFoundException(employeeId));

        current.setFirstName(employee.getFirstName());
        current.setLastName(employee.getLastName());
        current.setAge(employee.getAge());
        current.setPersonnelNumber(employee.getPersonnelNumber());
        current.setDepartment(employee.getDepartment());
        current.setJobDescription(employee.getJobDescription());
        current.setAnnualIncome(employee.getAnnualIncome());

        return save(current);
    }

    public Employee getRequired(UUID employeeId) {
        return findById(employeeId)
            .orElseThrow(() -> new EmployeeNotFoundException(employeeId));
    }

    @Transactional
    public void deleteById(UUID employeeId) {
        Employee current = getRequired(employeeId);
        delete(current);
    }
}
