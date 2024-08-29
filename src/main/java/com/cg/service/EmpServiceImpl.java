package com.cg.service;

//import java.io.StringReader;
//import java.io.StringWriter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.xml.bind.JAXBException;
//import jakarta.xml.bind.Marshaller;
//import jakarta.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.BO.EmpBO;
import com.cg.DAO.EmpDAO;
import com.cg.EO.EmpEO;
import com.cg.util.JaxbUtil;
import com.cg.EmpMap;

@Service
public class EmpServiceImpl implements EmpService {

    private static final Logger logger = LoggerFactory.getLogger(EmpServiceImpl.class);

    private final EmpDAO empDAO;
    private final EmpMap employeeMap;

    @Autowired
    public EmpServiceImpl(EmpDAO empDAO, EmpMap employeeMap) {
        this.empDAO = empDAO;
        this.employeeMap = employeeMap;
    }

    @Override
    public EmpBO createEmployee(EmpBO empBO) {
        logger.info("Creating employee: {}", empBO);
        EmpEO empEO = employeeMap.employee_EO(empBO);
        EmpEO createdEmpEO = empDAO.save(empEO); 
        EmpBO createdEmpBO = employeeMap.employee_BO(createdEmpEO);

        try {
            // Convert created EmpBO to JSON (Marshalling)
            String json = JaxbUtil.toJson(createdEmpBO);
            logger.info("Created Employee JSON: {}", json);

            // Convert JSON back to EmpBO (Unmarshalling)
            EmpBO unmarshalledEmpBO = JaxbUtil.fromJson(json, EmpBO.class);
            logger.info("Unmarshalled EmployeeBO: {}", unmarshalledEmpBO);

        } catch (JAXBException e) {
            logger.error("Error in JSON Conversion", e);
        }

        return createdEmpBO;
    }

    @Override
    public List<EmpBO> getAllEmployees() {
        logger.info("Retrieving all employees");
        List<EmpEO> employees = empDAO.findAll(); 
        return employees.stream()
                .map(employeeMap::employee_BO)
                .collect(Collectors.toList());
    }

    @Override
    public EmpBO getEmployeeById(Long id) {
        logger.info("Retrieving employee by ID: {}", id);
        EmpEO employee = empDAO.findById(id).orElse(null);
        
        if (employee == null) {
            logger.warn("Employee with ID: {} not found", id);
            return null; // Return null if the employee is not found
        }

        EmpBO employeeBO = employeeMap.employee_BO(employee);
        
        try {
            // Convert EmpBO to JSON (Marshalling)
            String json = JaxbUtil.toJson(employeeBO);
            logger.info("Retrieved Employee JSON: {}", json);

            // Convert JSON back to EmpBO (Unmarshalling)
            EmpBO unmarshalledEmpBO = JaxbUtil.fromJson(json, EmpBO.class);
            logger.info("Unmarshalled EmployeeBO: {}", unmarshalledEmpBO);

        } catch (JAXBException e) {
            logger.error("Error in JSON Conversion", e);
        }
        
        return employeeBO;
    }
    
//    @Override
//    public Optional<EmpBO> getEmployeeById(Long id) {
//        logger.info("Retrieving employee by ID: {}", id);
//        Optional<EmpEO> employee = empDAO.findById(id);
//        return employee.map(emp -> {
//            EmpBO employeeBO = employeeMap.employee_BO(emp);
//            try {
//                // Convert EmpBO to JSON (Marshalling)
//                String json = JaxbUtil.toJson(employeeBO);
//                logger.info("Retrieved Employee JSON: {}", json);
//
//                // Convert JSON back to EmpBO (Unmarshalling)
//                EmpBO unmarshalledEmpBO = JaxbUtil.fromJson(json, EmpBO.class);
//                logger.info("Unmarshalled EmployeeBO: {}", unmarshalledEmpBO);
//
//            } catch (JAXBException e) {
//                logger.error("Error in JSON Conversion", e);
//            }
//            return employeeBO;
//        });
//    }
}

//@Service
//public class EmpServiceImpl implements EmpService {
//
//    private static final Logger logger = LoggerFactory.getLogger(EmpServiceImpl.class);
//
//    private final EmpMap employeeMap;
//    private final EmpDAO empDAO;
//
//    private final Marshaller jaxbMarshaller;
//    private final Unmarshaller jaxbUnmarshaller;
//
//    @Autowired
//    public EmpServiceImpl(EmpMap empMap, EmpDAO empDAO, Marshaller jaxbMarshaller, 
//            Unmarshaller jaxbUnmarshaller) {
//        this.employeeMap = empMap;
//        this.empDAO = empDAO;
//        this.jaxbMarshaller = jaxbMarshaller;
//        this.jaxbUnmarshaller = jaxbUnmarshaller;
//    }
//
//    @Override
//    public List<EmpBO> getAllEmployees() {
//        logger.debug("Fetching all employees");
//        return empDAO.findAll().stream()
//                .map(employeeMap::employee_BO)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public EmpBO getEmployeeById(Long id) {
//        logger.debug("Fetching employee by id: {}", id);
//        return empDAO.findById(id)
//                .map(employeeMap::employee_BO)
//                .orElse(null);
//    }
//
//    @Override
//    public EmpBO createEmployee(EmpBO empBO) {
//        logger.debug("Creating employee: {}", empBO);
//        EmpEO empEO = employeeMap.employee_EO(empBO);
//        empEO = empDAO.save(empEO);
//        return employeeMap.employee_BO(empEO);
//    }
//
//    public String convertEmpBOToXml(EmpBO empBO) throws JAXBException {
//        StringWriter writer = new StringWriter();
//        jaxbMarshaller.marshal(empBO, writer);
//        return writer.toString();
//    }
//
//    public EmpBO convertXmlToEmpBO(String xml) throws JAXBException {
//        return (EmpBO) jaxbUnmarshaller.unmarshal(new StringReader(xml));
//    }
//
//    public String convertEmpEOToXml(EmpEO empEO) throws JAXBException {
//        StringWriter writer = new StringWriter();
//        jaxbMarshaller.marshal(empEO, writer);
//        return writer.toString();
//    }
//
//    public EmpEO convertXmlToEmpEO(String xml) throws JAXBException {
//        return (EmpEO) jaxbUnmarshaller.unmarshal(new StringReader(xml));
//    }
//}
