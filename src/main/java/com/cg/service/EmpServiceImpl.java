package com.cg.service;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.BO.EmpBO;
import com.cg.DAO.EmpDAO;
import com.cg.EO.EmpEO;
import com.cg.EmpMap;

@Service
public class EmpServiceImpl implements EmpService {

    private static final Logger logger = LoggerFactory.getLogger(EmpServiceImpl.class);

    private final EmpMap employeeMap;
    private final EmpDAO empDAO;

    private final Marshaller jaxbMarshaller;
    private final Unmarshaller jaxbUnmarshaller;

    @Autowired
    public EmpServiceImpl(EmpMap empMap, EmpDAO empDAO, Marshaller jaxbMarshaller, 
            Unmarshaller jaxbUnmarshaller) {
        this.employeeMap = empMap;
        this.empDAO = empDAO;
        this.jaxbMarshaller = jaxbMarshaller;
        this.jaxbUnmarshaller = jaxbUnmarshaller;
    }

    @Override
    public List<EmpBO> getAllEmployees() {
        logger.debug("Fetching all employees");
        return empDAO.findAll().stream()
                .map(employeeMap::employee_BO)
                .collect(Collectors.toList());
    }

    @Override
    public EmpBO getEmployeeById(Long id) {
        logger.debug("Fetching employee by id: {}", id);
        return empDAO.findById(id)
                .map(employeeMap::employee_BO)
                .orElse(null);
    }

    @Override
    public EmpBO createEmployee(EmpBO empBO) {
        logger.debug("Creating employee: {}", empBO);
        EmpEO empEO = employeeMap.employee_EO(empBO);
        empEO = empDAO.save(empEO);
        return employeeMap.employee_BO(empEO);
    }

    public String convertEmpBOToXml(EmpBO empBO) throws JAXBException {
        StringWriter writer = new StringWriter();
        jaxbMarshaller.marshal(empBO, writer);
        return writer.toString();
    }

    public EmpBO convertXmlToEmpBO(String xml) throws JAXBException {
        return (EmpBO) jaxbUnmarshaller.unmarshal(new StringReader(xml));
    }

    public String convertEmpEOToXml(EmpEO empEO) throws JAXBException {
        StringWriter writer = new StringWriter();
        jaxbMarshaller.marshal(empEO, writer);
        return writer.toString();
    }

    public EmpEO convertXmlToEmpEO(String xml) throws JAXBException {
        return (EmpEO) jaxbUnmarshaller.unmarshal(new StringReader(xml));
    }
}
