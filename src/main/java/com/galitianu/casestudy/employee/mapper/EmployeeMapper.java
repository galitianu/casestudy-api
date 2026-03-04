package com.galitianu.casestudy.employee.mapper;

import com.galitianu.casestudy.base.mapper.BaseModelDtoMapper;
import com.galitianu.casestudy.base.mapper.BaseModelEntityMapper;
import com.galitianu.casestudy.employee.persistence.entity.EmployeeEntity;
import com.galitianu.casestudy.employee.service.model.Employee;
import com.galitianu.casestudy.openapi.model.EmployeeRequest;
import com.galitianu.casestudy.openapi.model.EmployeeResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper extends BaseModelEntityMapper<Employee, EmployeeEntity>,
    BaseModelDtoMapper<Employee, EmployeeRequest, EmployeeResponse> {
}
