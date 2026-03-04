Feature: Employee API

  Background:
    Given the employee catalog is reset to the reference data

  Scenario: List all seeded employees
    When I request all employees
    Then the response status is 200
    And the employee response list has 6 entries
    And the employee list contains personnel number "mm1288_se"
    And the employee list contains personnel number "mm5482_pm"
    And the employee list contains personnel number "mm8523_hr"

  Scenario: Fetch a seeded employee by id
    When I request the seeded employee with personnel number "mm1288_se"
    Then the response status is 200
    And the employee response contains:
      | firstName       | Max                  |
      | lastName        | Mustermann           |
      | age             | 38                   |
      | personnelNumber | mm1288_se            |
      | department      | SOFTWARE_DEVELOPMENT |
      | jobDescription  | Backend-Entwickler   |
      | annualIncome    | 75000                |

  Scenario: Create a new employee
    When I create an employee with:
      | firstName       | Ada                |
      | lastName        | Lovelace           |
      | age             | 36                 |
      | personnelNumber | al1001_se          |
      | department      | SOFTWARE_DEVELOPMENT |
      | jobDescription  | Platform Engineer  |
      | annualIncome    | 99000              |
    Then the response status is 201
    And the employee response contains:
      | firstName       | Ada                |
      | lastName        | Lovelace           |
      | age             | 36                 |
      | personnelNumber | al1001_se          |
      | department      | SOFTWARE_DEVELOPMENT |
      | jobDescription  | Platform Engineer  |
      | annualIncome    | 99000              |
    And the created employee can be loaded again

  Scenario: Update an existing employee
    When I update the seeded employee with personnel number "ff4711_hr" with:
      | firstName       | Fiona                 |
      | lastName        | Fischer                |
      | age             | 28                    |
      | personnelNumber | ff4711_hr             |
      | department      | HUMAN_RESOURCES       |
      | jobDescription  | Senior Recruiterin    |
      | annualIncome    | 56000                 |
    Then the response status is 200
    And the employee response contains:
      | id              | ab22da22-94cb-4d46-b205-f1d0e387d780 |
      | firstName       | Fiona                 |
      | lastName        | Fischer                |
      | age             | 28                    |
      | personnelNumber | ff4711_hr             |
      | department      | HUMAN_RESOURCES       |
      | jobDescription  | Senior Recruiterin    |
      | annualIncome    | 56000                 |

  Scenario: Delete an existing employee
    When I delete the seeded employee with personnel number "aa2376_se"
    Then the response status is 204
    And the seeded employee with personnel number "aa2376_se" is no longer available

  Scenario: Reject an invalid employee id
    When I request employee "not-a-uuid"
    Then the response status is 400
    And the error response contains code "VALIDATION_ERROR"
    And the error response message is "employeeId must be a valid UUID"
