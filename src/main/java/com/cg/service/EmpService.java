package com.cg.service;

import java.util.List;
import com.cg.BO.EmpBO;

public interface EmpService {

	List<EmpBO> getAllEmployees();
    EmpBO getEmployeeById(Long id);
    EmpBO createEmployee(EmpBO empBO);
 
}
