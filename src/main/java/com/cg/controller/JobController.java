package com.cg.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/batch")
public class JobController {

    private static final Logger logger = LoggerFactory.getLogger(JobController.class);

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job h2ToCsvJob;
    
    @Autowired
    private Job csvToH2Job;
    
    @Autowired
    private Job taskletJob;

    @GetMapping("/startH2ToCsvJob")
    public BatchStatus startH2ToCsvJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        JobExecution jobExecution = jobLauncher.run(h2ToCsvJob, jobParameters);

        logger.info("H2 to CSV Job {} done...", jobExecution.getJobInstance().getJobName());
        return jobExecution.getStatus();
    }
    
    @GetMapping("/startCsvToH2Job")
    public BatchStatus startCsvToH2Job() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        JobExecution jobExecution = jobLauncher.run(csvToH2Job, jobParameters);

        logger.info("H2 to CSV Job {} done...", jobExecution.getJobInstance().getJobName());
        return jobExecution.getStatus();
    }
    
    @GetMapping("/startTaskletJob")
    public BatchStatus startTaskletJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        JobExecution jobExecution = jobLauncher.run(taskletJob, jobParameters);

        logger.info("Tasklet Job {} done...", jobExecution.getJobInstance().getJobName());
        return jobExecution.getStatus();
    }
}
