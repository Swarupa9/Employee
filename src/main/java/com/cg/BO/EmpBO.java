package com.cg.BO;

import java.io.Serializable;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
public class EmpBO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String emp_Name;
	private String emp_EmailId;
	private int emp_Age;
	

}
