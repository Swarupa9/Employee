package com.cg.EO;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
//@XmlRootElement
@Table(name = "employee")
public class EmpEO {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull(message = "Employee name cannot be null")
	private String emp_Name;
	
	@Email(message = "Email should be valid")
	private String emp_EmailId;
	
	@Min(value = 18, message = "Age should not be less than 18")
    @Max(value = 60, message = "Age should not be more than 60")
	private int emp_Age;
	
	
}