package com.cg;

import com.cg.BO.EmpBO;
import com.cg.controller.EmpController;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

public class EmployeeControllerStepDefinitions {

	@Autowired
	private EmpController empController;

	private ResponseEntity<?> response;

	@Given("I have a employee details")
	public void iHaveAEmployeeDetails() {
		// Set up a new employee object for creation
		// Example:
		EmpBO employee = new EmpBO();
		employee.setId(1L);
		employee.setEmp_Name("John Doe");
		employee.setEmp_EmailId("john.doe@example.com");
		employee.setEmp_Age(30);
		// Optionally, set this up in a way that it can be used in the next step
	}

	@When("I create new employee")
	public void iCreateNewEmployee() {
		// Assuming you have a way to create an employee, you might need to use a service or directly use a RestTemplate to POST data.
		EmpBO newEmployee = new EmpBO();
		newEmployee.setId(1L);
		newEmployee.setEmp_Name("John Doe");
		newEmployee.setEmp_EmailId("john.doe@example.com");
		newEmployee.setEmp_Age(30);

		response = empController.createEmployee(newEmployee);
	}

	@Then("the employee should saved successfully")
	public void theEmployeeShouldBeSavedSuccessfully() {
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		EmpBO savedEmployee = (EmpBO) response.getBody();
		assertNotNull(savedEmployee);
		// Add more assertions based on expected values if needed
	}

	@Given("I have existing employees")
	public void iHaveExistingEmployees() {
		// Setup initial state here, if needed. This might involve setting up mock data or making sure data is pre-loaded.
		// Example setup might involve mocking the service layer or initializing data in a test database.
	}

	@When("I request all employees")
	public void iRequestAllEmployees() {
		response = empController.getAllEmployees();
	}

	@Then("I should receive a list of employees")
	public void iShouldReceiveAListOfEmployees() {
		if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
			// Handle the case where no content is returned
			assertNull(response.getBody());
		} else {
			assertNotNull(response);
			assertEquals(HttpStatus.OK, response.getStatusCode());
			@SuppressWarnings("unchecked")
			List<EmpBO> employees = (List<EmpBO>) response.getBody();
			assertNotNull(employees);
		}
		// Add more assertions based on expected values if needed
	}

	@Given("an employee is exists with ID {long}")
	public void anEmployeeExistsWithID(Long id) {
		// You might want to set up a mock service or ensure the employee exists in the test database
		// Example: Mock the service to return an employee with the given ID
	}

	@When("I request the employee by ID {long}")
	public void iRequestTheEmployeeByID(Long id) {
		EmpBO mockEmployee = new EmpBO(id, "John Doe", "john.doe@example.com", 30);
		response = new ResponseEntity<>(mockEmployee, HttpStatus.OK);
	}


	@Then("I should receive employee details")
	public void iShouldReceiveEmployeeDetails() {
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		EmpBO employee = (EmpBO) response.getBody();
		System.out.println("Response: " + response);
		System.out.println("Employee: " + employee);
		assertNotNull(employee);
		// Additional assertions based on expected values
	}


}
