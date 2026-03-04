package com.galitianu.casestudy.employee.service;

import com.galitianu.casestudy.base.mapper.BaseModelEntityMapper;
import com.galitianu.casestudy.base.persistence.repository.BaseRepository;
import com.galitianu.casestudy.base.service.BaseEntityService;
import com.galitianu.casestudy.employee.mapper.EmployeeMapper;
import com.galitianu.casestudy.employee.persistence.entity.EmployeeEntity;
import com.galitianu.casestudy.employee.persistence.repository.EmployeeRepository;
import com.galitianu.casestudy.employee.service.model.Employee;
import lombok.RequiredArgsConstructor;

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
}
