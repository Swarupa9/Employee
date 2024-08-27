package com.cg.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.cg.batch.tasklet.TaskletItemProcessor;
import com.cg.batch.tasklet.TaskletItemReader;
import com.cg.batch.tasklet.TaskletItemWriter;


@Configuration
//@EnableBatchProcessing
public class JobConfigTasklet {

    @Bean
    @Qualifier("taskletJob")
    public Job taskletJob(JobRepository jobRepository,
                          @Qualifier("taskletReaderStep") Step readerStep,
                          @Qualifier("taskletProcessorStep") Step processorStep,
                          @Qualifier("taskletWriterStep") Step writerStep) {
        return new JobBuilder("taskletJob", jobRepository)
                .start(readerStep)
                .next(processorStep)
                .next(writerStep)
                .build();
    }

    @Bean
    @Qualifier("taskletReaderStep")
    public Step readerStep(JobRepository jobRepository,
                           PlatformTransactionManager transactionManager,
                           TaskletItemReader taskletItemReader) {
        return new StepBuilder("taskletReaderStep", jobRepository)
                .tasklet(taskletItemReader, transactionManager)
                .build();
    }

    @Bean
    @Qualifier("taskletProcessorStep")
    public Step processorStep(JobRepository jobRepository,
                              PlatformTransactionManager transactionManager,
                              TaskletItemProcessor taskletItemProcessor) {
        return new StepBuilder("taskletProcessorStep", jobRepository)
                .tasklet(taskletItemProcessor, transactionManager)
                .build();
    }

    @Bean
    @Qualifier("taskletWriterStep")
    public Step writerStep(JobRepository jobRepository,
                           PlatformTransactionManager transactionManager,
                           TaskletItemWriter taskletItemWriter) {
        return new StepBuilder("taskletWriterStep", jobRepository)
                .tasklet(taskletItemWriter, transactionManager)
                .build();
    }
}
