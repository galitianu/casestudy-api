package com.galitianu.casestudy.employee.persistence.entity;

import com.galitianu.casestudy.base.persistence.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "employee")
@Getter
@Setter
public class EmployeeEntity extends BaseEntity {
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false)
    private int age;

    @Column(name = "personnel_number", nullable = false, unique = true)
    private String personnelNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DepartmentType department;

    @Column(name = "job_description", nullable = false)
    private String jobDescription;

    @Column(name = "annual_income", nullable = false)
    private int annualIncome;
}
