package com.app.akbar.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.akbar.entity.Employee;

public interface IEmployeeService {
	
	Integer saveEmployee(Employee emp);
	void updateEmployee(Employee emp);
	void deleteEmployee(Integer id);
	Employee getOneEmployee(Integer id);
	List<Employee> getAllEmployees();
	Page<Employee> getAllEmployees(Pageable pageable);

}
