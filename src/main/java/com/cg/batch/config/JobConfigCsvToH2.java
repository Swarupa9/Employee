package com.cg.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.cg.batch.EmployeeItemProcessor;
import com.cg.batch.EmployeeItemReaderCsv;
import com.cg.batch.EmployeeItemWriterCsv;
import com.cg.batch.JobCompletionListener;
import com.cg.EO.EmpEO;

@Configuration

public class JobConfigCsvToH2 {

	@Bean
	@Qualifier("csvToH2Job")
	public Job csvToH2Job(JobRepository jobRepository,
			@Qualifier("csvToH2Step") Step csvToH2Step,
			JobCompletionListener listener) {
		return new JobBuilder("csvToH2Job", jobRepository)
				.listener(listener)
				.start(csvToH2Step)
				.build();
	}

	@Bean
	@Qualifier("csvToH2Step")
	public Step csvToH2Step(JobRepository jobRepository,
			PlatformTransactionManager transactionManager,
			EmployeeItemReaderCsv reader,
			EmployeeItemProcessor processor,
			EmployeeItemWriterCsv writer) {
		return new StepBuilder("csvToH2Step", jobRepository)
				.<EmpEO, EmpEO>chunk(5, transactionManager)
				.reader(reader)
				.processor(processor)
				.writer(writer)
				.build();
	}

}
