package com.cg.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionListener implements JobExecutionListener {

    private static final Logger logger = LoggerFactory.getLogger(JobCompletionListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        logger.info("Job {} starting...", jobExecution.getJobInstance().getJobName());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus().isUnsuccessful()) {
            logger.error("Job {} failed with status {}", jobExecution.getJobInstance().getJobName(), jobExecution.getStatus());
        } else {
            logger.info("Job {} completed with status {}", jobExecution.getJobInstance().getJobName(), jobExecution.getStatus());
        }
    }
}
