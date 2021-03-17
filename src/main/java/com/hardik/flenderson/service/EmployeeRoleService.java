package com.hardik.flenderson.service;

import org.springframework.stereotype.Service;

import com.hardik.flenderson.repository.EmployeeRoleRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeRoleService {
	
	private final EmployeeRoleRepository employeeRoleRepository;

}
