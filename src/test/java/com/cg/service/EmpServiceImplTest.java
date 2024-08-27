package com.cg.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.cg.BO.EmpBO;
import com.cg.DAO.EmpDAO;
import com.cg.EO.EmpEO;
import com.cg.EmpMap;

@MockitoSettings(strictness = Strictness.LENIENT)
//@ExtendWith(MockitoExtension.class)
public class EmpServiceImplTest {

    @Mock
    private EmpDAO empDAO;

    @Mock
    private EmpMap employeeMap;

    @InjectMocks
    private EmpServiceImpl empService;
    
    private EmpEO eEo;
    private EmpBO eBo;

    public void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
        
        eBo = new EmpBO(1L,"abc","abc@gmail.com",23);
        eEo = new EmpEO(1L,"abc","abc@gmail.com",23);
           
    }

    @Test
    void testGetAllEmployee() {
        List<EmpEO> employeeDataList = Arrays.asList(eEo);
        List<EmpBO> employeeBoList = Arrays.asList(eBo);

        when(empDAO.findAll()).thenReturn(employeeDataList);
        when(employeeMap.employee_BO(any(EmpEO.class))).thenReturn(eBo);

        List<EmpBO> result = empService.getAllEmployees();

        assertEquals(employeeBoList, result);
    }
    
    @Test
    public void testGetEmployeeById() {
        // Arrange
        EmpEO empEO = new EmpEO();
        empEO.setId(1L);
        empEO.setEmp_Name("John Doe");
        empEO.setEmp_EmailId("john.doe@example.com");
        empEO.setEmp_Age(30);

        EmpBO empBO = new EmpBO();
        empBO.setId(1L);
        empBO.setEmp_Name("John Doe");
        empBO.setEmp_EmailId("john.doe@example.com");
        empBO.setEmp_Age(30);

        when(empDAO.findById(1L)).thenReturn(Optional.of(empEO));
        when(employeeMap.employee_BO(empEO)).thenReturn(empBO);

        // Act
        EmpBO result = empService.getEmployeeById(1L);

        System.out.println("Result from service: " + result);
        // Assert
       // assertNotNull(result);
        assertEquals("John Doe", result.getEmp_Name());
    }


    @Test
    public void testCreateEmployee() {
        
    	when(employeeMap.employee_EO(any(EmpBO.class))).thenReturn(eEo);
        when(empDAO.save(any(EmpEO.class))).thenReturn(eEo);
        when(employeeMap.employee_BO(any(EmpEO.class))).thenReturn(eBo);
		
		//empDAO.save(eEo);
		System.out.println(eBo);

        // Act
        EmpBO result = empService.createEmployee(eBo);

        // Debug output
        System.out.println("eBo: " + eBo);
        System.out.println("Result from createEmployee: " + result);

        // Assert
//        assertNotNull(result, "Expected non-null EmpBO but received null");
        assertEquals(eBo, result, "The created employee does not match the expected employee");
    }
 

    @Test
    public void testGetEmployeeById_NotFound() {
        // Arrange
        when(empDAO.findById(1L)).thenReturn(Optional.empty());

        // Act
        EmpBO result = empService.getEmployeeById(1L);

        // Assert
        assertNull(result);
    }
}
