package com.hardik.flenderson.service;

import org.springframework.stereotype.Service;

import com.hardik.flenderson.entity.Employee;
import com.hardik.flenderson.keycloak.dto.KeycloakUserDto;
import com.hardik.flenderson.repository.EmployeeRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeService {
	
	private final EmployeeRepository employeeRepository;

	public Employee getEmployee(KeycloakUserDto keyCloakUser) {
		if (employeeRepository.existsByEmailIdIgnoreCase(keyCloakUser.getEmail()))
			return employeeRepository.findByEmailIdIgnoreCase(keyCloakUser.getEmail()).get();
		else {
			final var employee = new Employee();
			employee.setFirstName(keyCloakUser.getFirstName());
			employee.setLastName(keyCloakUser.getLastName());
			employee.setEmailId(keyCloakUser.getEmail());
			return employeeRepository.save(employee);
		}
		
	}

}
