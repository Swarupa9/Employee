package com.cg.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.cg.BO.EmpBO;
import com.cg.service.EmpService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmpControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private EmpService empService;

    private EmpBO eBo;

    @BeforeEach
    public void setUp() {
        eBo = new EmpBO(1L, "abc", "abc@gmail.com", 23);
    }

    @Test
    public void testHealthCheck() {
        ResponseEntity<String> response = restTemplate.getForEntity("/emp/health", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Backend call is successful");
    }

    @Test
    public void testGetAllEmployees() {
        List<EmpBO> employeeBoList = Arrays.asList(eBo);
        when(empService.getAllEmployees()).thenReturn(employeeBoList);

        ResponseEntity<EmpBO[]> response = restTemplate.getForEntity("/emp", EmpBO[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()[0].getId()).isEqualTo(eBo.getId());
        assertThat(response.getBody()[0].getEmp_Name()).isEqualTo(eBo.getEmp_Name());
        assertThat(response.getBody()[0].getEmp_EmailId()).isEqualTo(eBo.getEmp_EmailId());
        assertThat(response.getBody()[0].getEmp_Age()).isEqualTo(eBo.getEmp_Age());
    }

    @Test
    public void testGetEmployeeById() {
        when(empService.getEmployeeById(1L)).thenReturn(eBo);

        ResponseEntity<EmpBO> response = restTemplate.getForEntity("/emp/1", EmpBO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(eBo.getId());
        assertThat(response.getBody().getEmp_Name()).isEqualTo(eBo.getEmp_Name());
        assertThat(response.getBody().getEmp_EmailId()).isEqualTo(eBo.getEmp_EmailId());
        assertThat(response.getBody().getEmp_Age()).isEqualTo(eBo.getEmp_Age());
    }

    @Test
    public void testCreateEmployee() {
        when(empService.createEmployee(any(EmpBO.class))).thenReturn(eBo);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<EmpBO> request = new HttpEntity<>(eBo, headers);

        ResponseEntity<EmpBO> response = restTemplate.postForEntity("/emp", request, EmpBO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(eBo.getId());
        assertThat(response.getBody().getEmp_Name()).isEqualTo(eBo.getEmp_Name());
        assertThat(response.getBody().getEmp_EmailId()).isEqualTo(eBo.getEmp_EmailId());
        assertThat(response.getBody().getEmp_Age()).isEqualTo(eBo.getEmp_Age());
    }
}
