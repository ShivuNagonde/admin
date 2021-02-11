package com.shivu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shivu.model.Employee;
import com.shivu.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public Employee saveEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	@Override
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	public Optional<Employee> getEmployeeById(int id) {
		return employeeRepository.findById(id);
	}

	@Override
	public Employee updateEmployee(Employee employee) {
		Optional<Employee> emp = employeeRepository.findById(employee.getId());
		if(emp.isPresent()) {
			Employee e = emp.get();
			e.setFirstName(employee.getFirstName());
			e.setLastName(employee.getLastName());
			e.setEmail(employee.getEmail());
			e.setPassword(employee.getPassword());
			employeeRepository.save(e);
		}
		return employee;
	}

	@Override
	public void deleteEmployeeById(int id) {
		employeeRepository.deleteById(id);
		
	}

	@Override
	public void deleteEmployees() {
		employeeRepository.deleteAll();
		
	}

}
