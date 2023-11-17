package com.app.akbar.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.app.akbar.entity.Employee;
import com.app.akbar.exception.EmployeeNotFoundException;
import com.app.akbar.repo.EmployeeRepository;
import com.app.akbar.service.IEmployeeService;
import com.app.akbar.util.EmployeeUtil;

@Service
public class EmployeeServiceImpl implements IEmployeeService {
	
	@Autowired
	private EmployeeRepository repo;

	public Integer saveEmployee(Employee emp) {
		//calculateEmployee(emp);
		EmployeeUtil.calEmployee(emp);
		return repo.save(emp).getEmpId();
	}

	public void updateEmployee(Employee emp) {
		//calculateEmployee(emp);
		EmployeeUtil.calEmployee(emp);
		repo.save(emp);
	}

	public void deleteEmployee(Integer id) {
		//repo.deleteById(id);
		/*Optional<Employee> opt = repo.findById(id);
		if(opt.isPresent()) {
			repo.delete(opt.get());
		} else {
			throw new EmployeeNotFoundException("EMPLOYEE '"+id+"' NOT FOUND");
		}*/
		repo.delete(this.getOneEmployee(id));
	}

	public Employee getOneEmployee(Integer id) {
		/*Optional<Employee> opt = repo.findById(id);
		return opt.get();*/
		return repo.findById(id).orElseThrow(() -> new EmployeeNotFoundException("EMPLOYEE '"+id+"' NOT FOUND..!"));
	}

	public List<Employee> getAllEmployees() {
		List<Employee> list = repo.findAll();
		return list;
	}
	
	public Page<Employee> getAllEmployees(Pageable pageable) {
		Page<Employee> pages= repo.findAll(pageable);
		return pages;
	}
	
	/*
	private void calculateEmployee(Employee emp) {
		double hra = emp.getEmpSal()*(12.0/100);
		double ta = emp.getEmpSal()*(4.0/100);
		emp.setEmpHra(hra);
		emp.setEmpTa(ta);
	}*/

}
