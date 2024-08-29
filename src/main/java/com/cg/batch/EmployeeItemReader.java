package com.cg.batch;

import java.util.Iterator;

import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.DAO.EmpDAO;
import com.cg.EO.EmpEO;

@Component
public class EmployeeItemReader implements ItemReader<EmpEO> {
    private final EmpDAO employeeRepository;
    private Iterator<EmpEO> employeeIterator;

    @Autowired
    public EmployeeItemReader(EmpDAO employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmpEO read() {
        if (employeeIterator == null) {
            employeeIterator = employeeRepository.findAll().iterator();
        }

        return employeeIterator.hasNext() ? employeeIterator.next() : null;
    }
}

