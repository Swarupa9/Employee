Feature: Employee Controller

  Scenario: Get All Employees
    Given I have existing employees
    When I request all employees
    Then I should receive a list of employees

  Scenario: Get Employee by ID
    Given an employee is exists with ID 1
    When I request the employee by ID 1
    Then I should receive employee details

  Scenario: Create New Employee
    Given I have a employee details
    When I create new employee
    Then the employee should saved successfully
