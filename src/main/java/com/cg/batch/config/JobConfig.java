package com.cg.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.cg.EO.EmpEO;
import com.cg.batch.EmployeeItemProcessor;
import com.cg.batch.EmployeeItemReader;
import com.cg.batch.EmployeeItemWriter;
import com.cg.batch.JobCompletionListener;

@Configuration

public class JobConfig {

    @Bean
    @Qualifier("employeeJob") 
    public Job h2ToCsvJob(JobRepository jobRepository,
    		@Qualifier("employeeStep") Step employeeStep,
                          JobCompletionListener listener) {
        return new JobBuilder("h2ToCsvJob", jobRepository)
                .listener(listener)
                .start(employeeStep)
                .build();
    }
    	
	@Bean
	@Qualifier("employeeStep") 
    public Step employeeStep(JobRepository jobRepository,
                             PlatformTransactionManager transactionManager,
                             EmployeeItemReader reader,
                             EmployeeItemProcessor processor,
                             EmployeeItemWriter writer) {
        return new StepBuilder("employeeStep", jobRepository)
                .<EmpEO, EmpEO>chunk(5, transactionManager) 
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
