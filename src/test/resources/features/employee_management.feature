Feature: Employee Management

Scenario: Create a new employee
    Given I have employee details
    When I create a new employee
    Then the employee should be saved successfully
    
Scenario: Fetch all employees
    Given the employee database is initialized
    When I request a list of all employees
    Then I should receive the list of all employees
    
Scenario: Retrieve employee details by ID
    Given An employee exists with ID 1
    When I request the details of the employee with ID 1
    Then I should receive the employee details