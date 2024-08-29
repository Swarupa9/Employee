package com.cg;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import com.cg.BO.EmpBO;
import com.cg.EO.EmpEO;


@Component
@Mapper(componentModel = "spring")
public interface EmpMap {
	
	EmpMap INSTANCE = Mappers.getMapper(EmpMap.class);

	EmpBO employee_BO(EmpEO empEO);  // Method to map from EmpEO (Entity Object) to EmpBO (Business Object)
	EmpEO employee_EO(EmpBO empBO);  // Method to map from EmpBO (Business Object) to EmpEO (Entity Object)
}
