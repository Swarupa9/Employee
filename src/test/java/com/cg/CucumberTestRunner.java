package com.cg;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;


@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/features", 
		glue =  "com.cg",
		plugin = { "pretty", "html:build/reports/cucumber-reports.html" }
		)

//@CucumberContextConfiguration
//@SpringBootTest
public class CucumberTestRunner {
}
