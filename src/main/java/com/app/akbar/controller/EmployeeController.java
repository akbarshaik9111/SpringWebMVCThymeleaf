package com.app.akbar.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.akbar.entity.Employee;
import com.app.akbar.exception.EmployeeNotFoundException;
import com.app.akbar.service.IEmployeeService;
import com.app.akbar.util.EmployeeUtil;

@RequestMapping("/v1/api/employee")
@Controller
public class EmployeeController {
	
	@Autowired
	private IEmployeeService service;
	
	/**
	 * Show Register page
	 * @return
	 */
	@GetMapping("/register")
	public String getRegister(Model model) {
		EmployeeUtil.createDeptList(model);
		return "EmployeeRegister";
	}
	
	/*
	 * 2. ON CLICK ON SUBMIT, READ DATA (@MODELATTRIBUTE)
	 * THIS METHOD IS USED TO READ FORM DATA AS MODEL ATTRIBUTE
	 * IT WILL MAKE CALL TO SERVICE METHOD BY PASSING SAME FORM OBJECT
	 * SERVICE METHOD RETURNS PK(ID)
	 * CONTROLLER RETURNS STRING MESSAGE BACK TO USING MODEL
	 * @param employee
	 * @param model
	 * @return
	 */
	@PostMapping("/saveemployee")
	public String saveEmployee(@ModelAttribute Employee employee, Model model) {
		Integer id = service.saveEmployee(employee);
		String message = new StringBuffer().append("EMPLOYEE '").append(id).append("' CREATED").toString();
		model.addAttribute("message", message);
		EmployeeUtil.createDeptList(model);
		return "EmployeeRegister";
	}
	
	/*
	 *  3. DISPLAY ALL ROWS AS TABLE
	 *  THIS METHOD IS EXECUTED FOR REQUEST URL /ALL + GET
	 *  IT WILL FETCH DATA FROM SERVICE AS LIST<T>
	 *  SEND THIS DATA TO UI(VIEW) USING MODEL(I)
	 *  IN UI USE TH:EACH"TEMPVARIABLE:${COLLECTIONNAME}" TO READ DATA
	 *  AND PRINT AS HTML TABLE
	 */
	
	/*
	@GetMapping("/all")
	public String showEmpData(Model model, @RequestParam(value="message", required = false) String message) {
		List<Employee> list = service.getAllEmployees();
		model.addAttribute("list", list);
		model.addAttribute("message", message);
		return "EmployeeData";
	}*/
	
	@GetMapping("/all")
	public String showEmpData(
			Model model, 
			@PageableDefault(page = 0, size = 3) Pageable pageable, 
			@RequestParam(value="message", required = false) String message) {
		//List<Employee> list = service.getAllEmployees();
		Page<Employee> page = service.getAllEmployees(pageable);
		model.addAttribute("list", page.getContent());
		model.addAttribute("page", page);
		model.addAttribute("message", message);
		return "EmployeeData";
	}
	
	/*
	 * 4. DELETE BASED ON ID
	 * ON CLICK DELETE HYPERLINK, A REQUEST IS  MADE BY BROWSER LOOKS LIKE
	 * /V1/API/EMPLOYEE/DELETE?ID=SOMEVALUE
	 * READ DATA USING ANNOTATION @REQUESTPARAM AND CALL SERVICE TO DELETE FROM DB.
	 * 
	 * JUST REDIRECT TO /ALL WITH ONE MESSAGE (REDIRECT ATTRIBUTE)
	 * THAT WILL DISPLAY ALL ROWS WITH MESSAGE
	 */
	@GetMapping("/delete")
	public String delete(@RequestParam("id") Integer empId, RedirectAttributes attributes) {
		String message = null;
		try {
			service.deleteEmployee(empId);
			message = "Employee '"+empId+"' Deleted Successfully..!";
		} catch (EmployeeNotFoundException e) {
			e.printStackTrace();
			message = e.getMessage();
		}
		attributes.addAttribute("message", message);
		return "redirect:all";
	}
	
	// 5. On click on Edit link(Hyperlink) Show data in edit form
	@GetMapping("/edit")
	public String showEditPage(@RequestParam("id") Integer empId, Model model, RedirectAttributes attributes) {
		String page = null;
		try {
			Employee employee = service.getOneEmployee(empId);
			model.addAttribute("employee", employee);
			EmployeeUtil.createDeptList(model);
			page = "EmployeeEdit";
		} catch (EmployeeNotFoundException e) {
			e.printStackTrace();
			attributes.addAttribute("message", e.getMessage());
			page="redirect:all";
		}
		return page;
	}
	
	// 6. Update Form Data
	@PostMapping("/update")
	public String updateEmployee(@ModelAttribute Employee employee, RedirectAttributes attributes) {
		service.updateEmployee(employee);
		String message = "Employee Updated Successfully..!";
		attributes.addAttribute("message", message);
		return "redirect:all";
	}

}
