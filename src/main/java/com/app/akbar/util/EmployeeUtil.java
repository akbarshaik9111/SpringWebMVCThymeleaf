package com.app.akbar.util;

import java.util.Arrays;
import java.util.List;

import org.springframework.ui.Model;

import com.app.akbar.entity.Employee;

public interface EmployeeUtil {
	public static void calEmployee(Employee emp) {
		double hra = emp.getEmpSal()*(12.0/100);
		double ta = emp.getEmpSal()*(4.0/100);
		emp.setEmpHra(hra);
		emp.setEmpTa(ta);
	}
	
	public static void createDeptList(Model model) {
		List<String> deptList = Arrays.asList("BA", "QA", "DEV", "PS", "ADMIN");
		model.addAttribute("deptList", deptList);
	}

}
