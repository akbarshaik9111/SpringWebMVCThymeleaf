package com.app.akbar.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.akbar.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
