package com.cg.batch.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cg.EmpMap;
import com.cg.BO.EmpBO;
import com.cg.DAO.EmpDAO;

import java.io.FileWriter;
import java.util.List;


@Component
public class TaskletItemWriter implements Tasklet {
	
	private static final Logger logger = LoggerFactory.getLogger(TaskletItemWriter.class);

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        List<EmpBO> processedEmpBOList = (List<EmpBO>) chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().get("processedEmpBOList");

     // Log the processed list before writing to CSV
        processedEmpBOList.forEach(empBO -> 
            logger.debug("TaskletItemWriter - Processed EmpBO: {}", empBO)
        );
        
        // Write processed data to CSV
        try (FileWriter writer = new FileWriter("src/main/resources/employeesTasklet.csv")) {
            writer.append("ID,Name,Email,Age\n");
            for (EmpBO empBO : processedEmpBOList) {
                writer.append(String.valueOf(empBO.getId()))  // Convert long to String
                        .append(",")
                        .append(empBO.getEmp_Name())
                        .append(",")
                        .append(empBO.getEmp_EmailId())
                        .append(",")
                        .append(String.valueOf(empBO.getEmp_Age()))  // Convert int to String
                        .append("\n");
            }
        }

        return RepeatStatus.FINISHED;
    }
}
