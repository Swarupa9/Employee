package com.cg.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.BO.EmpBO;
import com.cg.service.EmpService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.xml.bind.JAXBException;

@RestController
@RequestMapping("/emp")
@Tag(name = "Employee API", description = "Operations related to employees")

public class EmpController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmpController.class);

	@Autowired
	private EmpService empService;
	 
	@Operation(summary = "Health Check", description = "Check if the backend service is up and running.")
		@ApiResponse(responseCode = "200", description = "Backend is healthy")
	@GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Backend call is successful");
    }
	
	
	@Operation(summary = "Get all employees", description = "Retrieve a list of all employees.")
    @ApiResponse(responseCode = "200", description = "List of employees")
	@ApiResponse(responseCode = "204", description = "No employees found")
	@GetMapping
	public ResponseEntity<List<EmpBO>> getAllEmployees(){
		logger.info("Fetching all employees");
		List<EmpBO> employees = empService.getAllEmployees();
	    if (employees.isEmpty()) {
	        logger.info("No employees found.");
	        return ResponseEntity.noContent().build(); 
	    }
	    return ResponseEntity.ok(employees);
	}
	
	@Operation(summary = "Get employee by ID", description = "Retrieve a specific employee by their ID.")
    @ApiResponse(responseCode = "200", description = "Employee details")
    @ApiResponse(responseCode = "404", description = "Employee not found")
	@GetMapping("/{id}")
	public ResponseEntity<EmpBO> getEmployeeById(
			//@PathVariable("id") Long id)
			@Parameter(description = "ID of the employee to be retrieved")
			@PathVariable ("id") Long id) {
		logger.info("Fetching employee with id: {}", id);
		//return ResponseEntity.ok(empService.getEmployeeById(id));
		EmpBO empBO = empService.getEmployeeById(id);
	    
	    if (empBO != null) {
	        logger.info("Employee with id: {} found", id);
	        return ResponseEntity.ok(empBO);
	    } else {
	        logger.warn("Employee with id: {} not found", id);
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  
	    }
	}
	
	
	 @Operation(summary = "Create a new employee", description = "Add a new employee to the system.")
    @ApiResponse(responseCode = "201", description = "Employee created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
	@PostMapping
	public ResponseEntity<EmpBO> createEmployee(@Valid @RequestBody EmpBO empBO){
		logger.info("Creating new employee: {}", empBO);
		EmpBO createdEmployee = empService.createEmployee(empBO);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
	}
	
	
	 @GetMapping("/convertToXml/{id}")
	    public ResponseEntity<String> convertEmployeeToXml(@PathVariable("id") Long id) {
	        try {
	            EmpBO empBO = empService.getEmployeeById(id);
	            if (empBO != null) {
	                String empXml = empService.convertEmpBOToXml(empBO);
	                return ResponseEntity.ok(empXml);
	            } else {
	                return ResponseEntity.notFound().build();
	            }
	        } catch (JAXBException e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error converting to XML: " + e.getMessage());
	        }
	    }
	 
	 @GetMapping("/convertToJson/{id}")
	    public ResponseEntity<EmpBO> convertEmployeeToJson(@PathVariable("id") Long id) {
	        EmpBO empBO = empService.getEmployeeById(id);
	        if (empBO != null) {
	            return ResponseEntity.ok(empBO);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	 }
	 	
}
