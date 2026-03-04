package com.galitianu.casestudy.test.employee;

import com.fasterxml.jackson.core.type.TypeReference;
import com.galitianu.casestudy.openapi.model.EmployeeRequest;
import com.galitianu.casestudy.openapi.model.EmployeeResponse;
import com.galitianu.casestudy.openapi.model.ErrorResponse;
import com.galitianu.casestudy.test.base.ApiIntegrationTestSupport;
import com.galitianu.casestudy.test.employee.builder.EmployeeRequestTestBuilder;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeApiSteps extends ApiIntegrationTestSupport {

    private final EmployeeDatabaseReset employeeDatabaseReset;

    private MvcResult lastResult;
    private EmployeeResponse lastEmployeeResponse;
    private List<EmployeeResponse> lastEmployeeResponses;
    private ErrorResponse lastErrorResponse;

    public EmployeeApiSteps(
        EmployeeDatabaseReset employeeDatabaseReset,
        MockMvc mvc,
        ObjectMapper objectMapper
    ) {
        super(mvc, objectMapper);
        this.employeeDatabaseReset = employeeDatabaseReset;
    }

    @Before
    public void resetDatabase() {
        employeeDatabaseReset.resetToSeedData();
        clearResponseState();
    }

    @Given("the employee catalog is reset to the reference data")
    public void theEmployeeCatalogIsResetToTheReferenceData() {
        resetDatabase();
    }

    @When("I request all employees")
    public void iRequestAllEmployees() {
        remember(performGet("/employee"));
    }

    @When("I request employee {string}")
    public void iRequestEmployee(String employeeId) {
        remember(performGet("/employee/{employeeId}", employeeId));
    }

    @When("I request the seeded employee with personnel number {string}")
    public void iRequestTheSeededEmployeeWithPersonnelNumber(String personnelNumber) {
        iRequestEmployee(employeeDatabaseReset.idForPersonnelNumber(personnelNumber));
    }

    @When("I create an employee with:")
    public void iCreateAnEmployeeWith(DataTable dataTable) {
        EmployeeRequest employeeRequest = EmployeeRequestTestBuilder.from(dataTable.asMap(String.class, String.class));
        remember(performPost("/employee", employeeRequest));
    }

    @When("I update the seeded employee with personnel number {string} with:")
    public void iUpdateTheSeededEmployeeWithPersonnelNumberWith(String personnelNumber, DataTable dataTable) {
        String employeeId = employeeDatabaseReset.idForPersonnelNumber(personnelNumber);
        EmployeeRequest employeeRequest = EmployeeRequestTestBuilder.from(dataTable.asMap(String.class, String.class));
        remember(performPut("/employee/{employeeId}", employeeRequest, employeeId));
    }

    @When("I delete the seeded employee with personnel number {string}")
    public void iDeleteTheSeededEmployeeWithPersonnelNumber(String personnelNumber) {
        String employeeId = employeeDatabaseReset.idForPersonnelNumber(personnelNumber);
        remember(performDelete("/employee/{employeeId}", employeeId));
    }

    @Then("the response status is {int}")
    public void theResponseStatusIs(int status) {
        assertThat(lastResult.getResponse().getStatus()).isEqualTo(status);
    }

    @Then("the employee response list has {int} entries")
    public void theEmployeeResponseListHasEntries(int size) {
        assertThat(readEmployeeResponses()).hasSize(size);
    }

    @Then("the employee list contains personnel number {string}")
    public void theEmployeeListContainsPersonnelNumber(String personnelNumber) {
        assertThat(readEmployeeResponses())
            .extracting(EmployeeResponse::getPersonnelNumber)
            .contains(personnelNumber);
    }

    @Then("the employee response contains:")
    public void theEmployeeResponseContains(DataTable dataTable) {
        Map<String, String> expectedFields = dataTable.asMap(String.class, String.class);
        EmployeeResponse employeeResponse = readEmployeeResponse();

        expectedFields.forEach((field, expectedValue) ->
            assertThat(readEmployeeField(employeeResponse, field))
                .as(field)
                .isEqualTo(expectedValue)
        );
    }

    @Then("the created employee can be loaded again")
    public void theCreatedEmployeeCanBeLoadedAgain() {
        EmployeeResponse createdEmployee = readEmployeeResponse();
        remember(performGet("/employee/{employeeId}", createdEmployee.getId()));
        assertThat(lastResult.getResponse().getStatus()).isEqualTo(200);
        assertThat(readEmployeeResponse()).isEqualTo(createdEmployee);
    }

    @Then("the seeded employee with personnel number {string} is no longer available")
    public void theSeededEmployeeWithPersonnelNumberIsNoLongerAvailable(String personnelNumber) {
        remember(performGet("/employee/{employeeId}", employeeDatabaseReset.idForPersonnelNumber(personnelNumber)));
        assertThat(lastResult.getResponse().getStatus()).isEqualTo(404);
        assertThat(readErrorResponse().getCode()).isEqualTo("EMPLOYEE_NOT_FOUND");
    }

    @Then("the error response contains code {string}")
    public void theErrorResponseContainsCode(String code) {
        assertThat(readErrorResponse().getCode()).isEqualTo(code);
    }

    @Then("the error response message is {string}")
    public void theErrorResponseMessageIs(String message) {
        assertThat(readErrorResponse().getMessage()).isEqualTo(message);
    }

    private void remember(MvcResult mvcResult) {
        lastResult = mvcResult;
        clearResponseState();
    }

    private void clearResponseState() {
        lastEmployeeResponse = null;
        lastEmployeeResponses = null;
        lastErrorResponse = null;
    }

    private EmployeeResponse readEmployeeResponse() {
        if (lastEmployeeResponse == null) {
            lastEmployeeResponse = convert(lastResult, EmployeeResponse.class);
        }

        return lastEmployeeResponse;
    }

    private List<EmployeeResponse> readEmployeeResponses() {
        if (lastEmployeeResponses == null) {
            lastEmployeeResponses = convert(lastResult, new TypeReference<>() {
            });
        }

        return lastEmployeeResponses;
    }

    private ErrorResponse readErrorResponse() {
        if (lastErrorResponse == null) {
            lastErrorResponse = convert(lastResult, ErrorResponse.class);
        }

        return lastErrorResponse;
    }

    private String readEmployeeField(EmployeeResponse employeeResponse, String field) {
        return switch (field) {
            case "id" -> employeeResponse.getId();
            case "firstName" -> employeeResponse.getFirstName();
            case "lastName" -> employeeResponse.getLastName();
            case "age" -> normalize(employeeResponse.getAge());
            case "personnelNumber" -> employeeResponse.getPersonnelNumber();
            case "department" -> employeeResponse.getDepartment();
            case "jobDescription" -> employeeResponse.getJobDescription();
            case "annualIncome" -> normalize(employeeResponse.getAnnualIncome());
            default -> throw new IllegalArgumentException("Unsupported employee response field: " + field);
        };
    }

    private String normalize(BigDecimal value) {
        return value.stripTrailingZeros().toPlainString();
    }
}
