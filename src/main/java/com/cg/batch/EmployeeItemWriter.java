package com.cg.batch;


import java.io.File;
import java.io.FileWriter;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.cg.EO.EmpEO;

@Component
public class EmployeeItemWriter implements ItemWriter<EmpEO> {
    private static final String HEADER = "ID,Name,Email,Age\n";
    private final String outputFilePath = "src/main/resources/employeesH2ToCsv.csv";

    @Override
    public void write(Chunk<? extends EmpEO> employees) throws Exception {
        try (FileWriter writer = new FileWriter(outputFilePath, true)) {
            if (new File(outputFilePath).length() == 0) {
                writer.write(HEADER);
            }

            for (EmpEO employee : employees) {
                writer.write(employee.getId() + "," + employee.getEmp_Name() + "," + 
                             employee.getEmp_EmailId() + "," + employee.getEmp_Age() + "\n");
            }
        }
    }
}
