package com.shivu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shivu.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
