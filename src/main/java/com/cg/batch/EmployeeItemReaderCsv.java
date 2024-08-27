package com.cg.batch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
//import org.springframework.batch.item.csv.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;
//import org.springframework.batch.item.file.transform.BeanWrapperFieldSetMapper;
//import org.springframework.batch.item.file.transform.DefaultLineMapper;
//import org.springframework.batch.item.file.transform.LineMapper;
//import org.springframework.batch.item.file.transform.RecordSeparatorPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cg.EO.EmpEO;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


@Component
public class EmployeeItemReaderCsv implements ItemReader<EmpEO> {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeItemReaderCsv.class);
    private static final String FILE_NAME = "src/main/resources/employeesH2ToCsv.csv";
    private BufferedReader reader;
    private String currentLine;
    private boolean isFirstLine = true;

    public EmployeeItemReaderCsv() {
        try {
            reader = new BufferedReader(new FileReader(FILE_NAME));
        } catch (IOException e) {
            logger.error("Error opening the CSV file: {}", FILE_NAME, e);
        	
            throw new RuntimeException("Failed to initialize EmployeeItemReaderCsv", e);
        }
    }

    @Override
    public EmpEO read() {
        try {
            if (isFirstLine) {
                // Skip the header line
                reader.readLine();
                isFirstLine = false;
            }

            if ((currentLine = reader.readLine()) != null) {
                String[] employeeData = currentLine.split(",");
                if (employeeData.length != 4) { // Ensure the length matches your CSV columns
                    logger.warn("Skipping invalid line: {}", currentLine);
                    return null;
                }

                EmpEO employee = new EmpEO();
                try {
                    employee.setId(Long.parseLong(employeeData[0]));
                    employee.setEmp_Name(employeeData[1]);
                    employee.setEmp_EmailId(employeeData[2]);
                    employee.setEmp_Age(Integer.parseInt(employeeData[3]));
                } catch (NumberFormatException e) {
                    logger.error("Error parsing employee data: {}", currentLine, e);
                    return null; // Skip this entry on error
                }

                logger.info("Reading employee: {}", employee);
                return employee;
            } else {
                reader.close();
                logger.info("End of file reached");
            }
        } catch (IOException e) {
            logger.error("Error reading the CSV file", e);
        }
        return null;
    }
}
//@Component
//public class EmployeeItemReaderCsv {
//
//    //@Value("classpath:src/main/resources/employeesH2ToCsv.csv") // Path to CSV file in resources
//    private String inputFile;
//
//    public FlatFileItemReader<EmpEO> reader() {
//        return new FlatFileItemReaderBuilder<EmpEO>()
//                .name("csvEmployeeItemReader")
//                .resource(new ClassPathResource("src/main/resources/employeesH2ToCsv.csv")) // Ensure this file is in src/main/resources
//                .delimited()
//                .names("ID", "Name", "Email", "Age") // Ensure these match your CSV header names
//                .fieldSetMapper(new BeanWrapperFieldSetMapper<EmpEO>() {{
//                    setTargetType(EmpEO.class);
//                }})
//                .build();
//    }
//    
// .names("id", "emp_Name", "emp_EmailId", "emp_Age")
    
//    public FlatFileItemReader<EmpEO> reader() {
//        LineMapper<EmpEO> lineMapper = new DefaultLineMapper<EmpEO>() {{
//            setLineTokenizer(new DelimitedLineTokenizer() {{
//                setNames("id", "emp_Name", "emp_EmailId", "emp_Age");
//            }});
//            setFieldSetMapper(new BeanWrapperFieldSetMapper<EmpEO>() {{
//                setTargetType(EmpEO.class);
//            }});
//        }};
//        
//        return new FlatFileItemReaderBuilder<EmpEO>()
//                .name("employeeItemReader")
//                .resource(new ClassPathResource(inputFile))
//                .lineMapper(lineMapper)
//                .build();
//    }

