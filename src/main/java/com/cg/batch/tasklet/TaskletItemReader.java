package com.cg.batch.tasklet;

import org.springframework.batch.core.StepContribution;
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
import com.cg.EO.EmpEO;

import java.util.List;

@Component
public class TaskletItemReader implements Tasklet {
	
	private static final Logger logger = LoggerFactory.getLogger(TaskletItemWriter.class);

    private final EmpDAO employeeRepository;
    private final EmpMap employeeMapper;
    private List<EmpBO> empBOList;

    @Autowired
    public TaskletItemReader(EmpDAO employeeRepository, EmpMap employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        List<EmpEO> empEOList = employeeRepository.findAll();
        empBOList = empEOList.stream()
                .map(employeeMapper::employee_BO)
                .toList();
        // Log the list of EmpBOs
        empBOList.forEach(empBO -> 
        logger.debug("TaskletItemReader - EmpBO: " + empBO)
        );
        
        chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("empBOList", empBOList);
        return RepeatStatus.FINISHED;
    }
}

