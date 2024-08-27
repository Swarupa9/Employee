package com.cg;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cg.BO.EmpBO;
import com.cg.service.EmpService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EmployeeStepDefinitions {

    @Autowired
    private EmpService empService;

    private EmpBO empBO;
    private List<EmpBO> employeeList = new ArrayList<>(); // Initialized here

    @Given("I have employee details")
    public void iHaveEmployeeDetails() {
        empBO = new EmpBO(1L, "John Doe", "john.doe@example.com", 30);
    }

    @When("I create a new employee")
    public void iCreateANewEmployee() {
        empBO = empService.createEmployee(empBO);
    }

    @Then("the employee should be saved successfully")
    public void theEmployeeShouldBeSavedSuccessfully() {
        assertNotNull(empBO);
        assertEquals("John Doe", empBO.getEmp_Name());
    }

    @Given("the employee database is initialized")
    public void theEmployeeDatabaseIsInitialized() {
        // Assuming database initialization is done before running the tests.
    }

    @When("I request a list of all employees")
    public void iRequestAListOfAllEmployees() {
        employeeList = empService.getAllEmployees();
    }

    @Then("I should receive the list of all employees")
    public void iShouldReceiveTheListOfAllEmployees() {
        assertNotNull(employeeList);
        assertFalse(employeeList.isEmpty());
    }
    
    @Given("An employee exists with ID {long}")
    public void anEmployeeExistsWithID(Long id) {
        empBO = empService.getEmployeeById(id);
        assertNotNull(empBO, "Employee should exist for the given ID");
    }

    @When("I request the details of the employee with ID {long}")
    public void iRequestTheDetailsOfTheEmployeeWithID(Long id) {
        empBO = empService.getEmployeeById(id);
    }

    @Then("I should receive the employee details")
    public void iShouldReceiveTheEmployeeDetails() {
        assertNotNull(empBO, "Employee details should be retrieved");
    }
}
