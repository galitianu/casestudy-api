package com.galitianu.casestudy.employee.mapper;

import com.galitianu.casestudy.base.mapper.BaseModelEntityMapper;
import com.galitianu.casestudy.base.mapper.BaseModelDtoMapper;
import com.galitianu.casestudy.employee.persistence.entity.EmployeeEntity;
import com.galitianu.casestudy.employee.service.model.Employee;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class EmployeeMapper implements BaseModelEntityMapper<Employee, EmployeeEntity>, BaseModelDtoMapper<Employee,EmployeeDto > {
}
