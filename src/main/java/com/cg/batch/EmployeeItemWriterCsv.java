package com.cg.batch;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.DAO.EmpDAO;
import com.cg.EO.EmpEO;

import jakarta.persistence.EntityManagerFactory;
//import javax.persistence.EntityManagerFactory;

@Component
public class EmployeeItemWriterCsv implements ItemWriter<EmpEO> {

    @Autowired
    private EmpDAO employeeRepository;

    public void write(Chunk<? extends EmpEO> item) throws Exception {
        employeeRepository.saveAll(item.getItems());
    }
}
