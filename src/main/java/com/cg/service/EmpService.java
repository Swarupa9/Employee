package com.cg.service;

import java.util.List;

import com.cg.BO.EmpBO;
import com.cg.EO.EmpEO;

import jakarta.xml.bind.JAXBException;

public interface EmpService {

	List<EmpBO> getAllEmployees();
    EmpBO getEmployeeById(Long id);
    EmpBO createEmployee(EmpBO empBO);
    
    String convertEmpBOToXml(EmpBO empBO) throws JAXBException;
    EmpBO convertXmlToEmpBO(String xml) throws JAXBException;
    String convertEmpEOToXml(EmpEO empEO) throws JAXBException;
    EmpEO convertXmlToEmpEO(String xml) throws JAXBException;
    
}
