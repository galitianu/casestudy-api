package com.galitianu.casestudy.employee.service.model;

import com.galitianu.casestudy.base.service.BaseEntityModel;
import com.galitianu.casestudy.employee.persistence.entity.DepartmentType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Employee extends BaseEntityModel {
    private String firstName;

    private String lastName;

    private int age;

    private String personnelNumber;

    private DepartmentType department;

    private String jobDescription;

    private int annualIncome;
}
