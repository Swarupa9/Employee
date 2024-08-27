package com.cg.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.cg.EO.EmpEO;

@Component
public class EmployeeItemProcessor implements ItemProcessor<EmpEO, EmpEO> {
    @Override
    public EmpEO process(EmpEO employee) {
        // Example: You can manipulate the data here if necessary
        //employee.setEmp_Name(employee.getEmp_Name().toUpperCase());
        return employee;
    }
}

