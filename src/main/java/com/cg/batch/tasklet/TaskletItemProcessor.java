package com.cg.batch.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cg.BO.EmpBO;
import java.util.List;

@Component
public class TaskletItemProcessor implements Tasklet {

	private static final Logger logger = LoggerFactory.getLogger(TaskletItemWriter.class);
	
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        List<EmpBO> empBOList = (List<EmpBO>) chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().get("empBOList");
        
     // Log the list before processing
        empBOList.forEach(empBO -> 
        logger.debug("TaskletItemProcessor - Before Processing - EmpBO: " + empBO)
        );
        
        // Perform any required processing here
        empBOList.forEach(empBO -> {
            // Example processing
            empBO.setEmp_Name(empBO.getEmp_Name().toUpperCase());
        });

     // Log the list after processing
        empBOList.forEach(empBO -> 
        logger.debug("TaskletItemProcessor - After Processing - EmpBO: " + empBO)
        );
        
        // Store processed data back in the context
        chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("processedEmpBOList", empBOList);

        return RepeatStatus.FINISHED;
    }
}
