package com.cg;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = EmpApp1Application.class) 

public class CucumberSpringConfiguration {
	
}
