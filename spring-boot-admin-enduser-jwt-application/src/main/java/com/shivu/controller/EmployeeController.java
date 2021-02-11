package com.shivu.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shivu.model.Employee;
import com.shivu.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@PostMapping(value = "/saveEmployee")
	public Employee saveEmployee(@RequestParam("file") MultipartFile file, Employee employee) throws IOException {
		String imageFile = encodeFileToBase64Binary(file.getBytes());
		employee.setImage(imageFile);
		return employeeService.saveEmployee(employee);
	}

	private String encodeFileToBase64Binary(byte[] bytes) {
		String encodedFile = "";
		try {
            encodedFile = Base64.getEncoder().encodeToString(bytes);
        }catch (Exception e) {
            e.printStackTrace();
        }
		return encodedFile;
	}

	 @GetMapping(value = "/video")
	 public ResponseEntity<InputStreamResource> retrieveResource() throws Exception {
	 File file = new File("C:\\Users\\Shivakumar\\Videos\\Ugramm\\");
	 InputStream inputStream = new FileInputStream(file);
	 HttpHeaders headers = new HttpHeaders();
	 headers.set("Accept-Ranges", "bytes");
	 headers.set("Content-Type", "video/mp4");
	 headers.set("Content-Range", "bytes 50-1025/17839845");
	 headers.set("Content-Length", String.valueOf(file.length()));
	 return new ResponseEntity<>(new InputStreamResource(inputStream), headers, HttpStatus.OK);

	 }
	
	@GetMapping(value = "/getAllEmployees")
	public List<Employee> getAllEmployees() {
		return employeeService.getAllEmployees();
	}

	@GetMapping(value = "/getEmployee/{id}")
	public Optional<Employee> getEmployeeById(@PathVariable int id) {
		return employeeService.getEmployeeById(id);
	}
    
	@PutMapping(value = "/updateEmployee")
	public Employee updateEmployee(@RequestBody Employee employee) {
		return employeeService.updateEmployee(employee);
	}

	@DeleteMapping(value = "/deleteEmployee")
	public void deleteEmployeeById(int id) {
		employeeService.deleteEmployeeById(id);
		
	}

	@DeleteMapping(value = "/deleteAllEmployees")
	public void deleteAllEmployees() {
		employeeService.deleteEmployees();
		
	}

}

