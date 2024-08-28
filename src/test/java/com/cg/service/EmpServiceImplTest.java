package com.cg.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cg.BO.EmpBO;
import com.cg.DAO.EmpDAO;
import com.cg.EO.EmpEO;
import com.cg.EmpMap;

public class EmpServiceImplTest {

    @Mock
    private EmpDAO empDAO;

    @Mock
    private EmpMap employeeMap;

    @InjectMocks
    private EmpServiceImpl empService;

    private EmpEO eEo;
    private EmpBO eBo;

    @BeforeEach
    public void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
        
        eBo = new EmpBO(1L, "John Doe", "john.doe@example.com", 30);
        eEo = new EmpEO(1L, "John Doe", "john.doe@example.com", 30);
    }

    @Test
    public void testGetAllEmployees() {
        // Arrange
        List<EmpEO> employeeDataList = Arrays.asList(eEo);
        List<EmpBO> employeeBoList = Arrays.asList(eBo);

        when(empDAO.findAll()).thenReturn(employeeDataList);
        when(employeeMap.employee_BO(any(EmpEO.class))).thenReturn(eBo);

        // Act
        List<EmpBO> result = empService.getAllEmployees();

        // Assert
        assertEquals(employeeBoList, result);
    }

    @Test
    public void testGetEmployeeById() {
        // Arrange
        when(empDAO.findById(1L)).thenReturn(Optional.of(eEo));
        when(employeeMap.employee_BO(eEo)).thenReturn(eBo);

        // Act
        EmpBO result = empService.getEmployeeById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(eBo, result);
    }

    @Test
    public void testCreateEmployee() {
        // Arrange
        when(employeeMap.employee_EO(any(EmpBO.class))).thenReturn(eEo);
        when(empDAO.save(any(EmpEO.class))).thenReturn(eEo);
        when(employeeMap.employee_BO(any(EmpEO.class))).thenReturn(eBo);

        // Act
        EmpBO result = empService.createEmployee(eBo);

        // Assert
        assertNotNull(result);
        assertEquals(eBo, result);
    }

    @Test
    void testGetEmployeeById_NotFound() {
        // Arrange
        when(empDAO.findById(1L)).thenReturn(Optional.empty());

        // Act
        EmpBO result = empService.getEmployeeById(1L);

        // Assert
        assertNull(result, "Expected null when employee is not found.");
    }
}
