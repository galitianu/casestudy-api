package com.galitianu.casestudy.test.employee;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class EmployeeDatabaseReset {

    private static final List<SeedEmployee> SEED_EMPLOYEES = List.of(
        new SeedEmployee("cb4881f5-2e40-4708-9bd0-c8e9f37d27bd", "Max", "Mustermann", 38, "mm1288_se", "SOFTWARE_DEVELOPMENT", "Backend-Entwickler", 75000),
        new SeedEmployee("302dc3aa-b002-4abf-95ea-a3438e1deff5", "Miriam", "Müller", 42, "mm5482_pm", "PROJECT_MANAGEMENT", "Projektmanagerin", 83000),
        new SeedEmployee("333cf860-694c-4827-a6c8-e15d66942b95", "Bertram", "Bauer", 56, "bb4849_se", "SOFTWARE_DEVELOPMENT", "Frontend-Entwickler", 72000),
        new SeedEmployee("ab22da22-94cb-4d46-b205-f1d0e387d780", "Fiona", "Fischer", 27, "ff4711_hr", "HUMAN_RESOURCES", "Recruiterin", 52000),
        new SeedEmployee("b999c5da-e19f-4337-bdd6-e6d0164dd64d", "Agathe", "Adams", 51, "aa2376_se", "SOFTWARE_DEVELOPMENT", "Frontend-Entwicklerin", 72000),
        new SeedEmployee("cd1ff86f-1bb6-42a0-a42e-50de2ece7aa4", "Moritz", "Meier", 32, "mm8523_hr", "HUMAN_RESOURCES", "Recruiter", 52000)
    );

    private static final Map<String, String> ID_BY_PERSONNEL_NUMBER = SEED_EMPLOYEES.stream()
        .collect(Collectors.toUnmodifiableMap(SeedEmployee::personnelNumber, SeedEmployee::id));

    private final JdbcTemplate jdbcTemplate;

    public EmployeeDatabaseReset(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void resetToSeedData() {
        jdbcTemplate.update("DELETE FROM employee");

        for (SeedEmployee seedEmployee : SEED_EMPLOYEES) {
            jdbcTemplate.update(
                """
                    INSERT INTO employee (
                        id, version, created, updated, first_name, last_name, age,
                        personnel_number, department, job_description, annual_income
                    ) VALUES (?, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?, ?, ?)
                    """,
                UUID.fromString(seedEmployee.id()),
                seedEmployee.firstName(),
                seedEmployee.lastName(),
                seedEmployee.age(),
                seedEmployee.personnelNumber(),
                seedEmployee.department(),
                seedEmployee.jobDescription(),
                seedEmployee.annualIncome()
            );
        }
    }

    public String idForPersonnelNumber(String personnelNumber) {
        String id = ID_BY_PERSONNEL_NUMBER.get(personnelNumber);
        if (id == null) {
            throw new IllegalArgumentException("Unknown seeded personnel number: " + personnelNumber);
        }

        return id;
    }

    private record SeedEmployee(
        String id,
        String firstName,
        String lastName,
        int age,
        String personnelNumber,
        String department,
        String jobDescription,
        int annualIncome
    ) {
    }
}
