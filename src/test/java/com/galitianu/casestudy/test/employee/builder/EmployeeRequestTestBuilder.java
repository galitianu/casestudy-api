package com.galitianu.casestudy.test.employee.builder;

import com.galitianu.casestudy.openapi.model.EmployeeRequest;

import java.math.BigDecimal;
import java.util.Map;

public final class EmployeeRequestTestBuilder {

    private String firstName = "Test";
    private String lastName = "Employee";
    private BigDecimal age = BigDecimal.valueOf(31);
    private String personnelNumber = "te0001_test";
    private String department = "SOFTWARE_DEVELOPMENT";
    private String jobDescription = "Engineer";
    private BigDecimal annualIncome = BigDecimal.valueOf(65000);

    private EmployeeRequestTestBuilder() {
    }

    public static EmployeeRequestTestBuilder anEmployeeRequest() {
        return new EmployeeRequestTestBuilder();
    }

    public static EmployeeRequest from(Map<String, String> fields) {
        EmployeeRequestTestBuilder builder = anEmployeeRequest();
        fields.forEach(builder::apply);
        return builder.build();
    }

    public EmployeeRequestTestBuilder apply(String field, String value) {
        return switch (field) {
            case "firstName" -> withFirstName(value);
            case "lastName" -> withLastName(value);
            case "age" -> withAge(value);
            case "personnelNumber" -> withPersonnelNumber(value);
            case "department" -> withDepartment(value);
            case "jobDescription" -> withJobDescription(value);
            case "annualIncome" -> withAnnualIncome(value);
            default -> throw new IllegalArgumentException("Unsupported employee field: " + field);
        };
    }

    public EmployeeRequestTestBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public EmployeeRequestTestBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public EmployeeRequestTestBuilder withAge(String age) {
        this.age = new BigDecimal(age);
        return this;
    }

    public EmployeeRequestTestBuilder withPersonnelNumber(String personnelNumber) {
        this.personnelNumber = personnelNumber;
        return this;
    }

    public EmployeeRequestTestBuilder withDepartment(String department) {
        this.department = department;
        return this;
    }

    public EmployeeRequestTestBuilder withJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
        return this;
    }

    public EmployeeRequestTestBuilder withAnnualIncome(String annualIncome) {
        this.annualIncome = new BigDecimal(annualIncome);
        return this;
    }

    public EmployeeRequest build() {
        return new EmployeeRequest()
            .firstName(firstName)
            .lastName(lastName)
            .age(age)
            .personnelNumber(personnelNumber)
            .department(department)
            .jobDescription(jobDescription)
            .annualIncome(annualIncome);
    }
}
