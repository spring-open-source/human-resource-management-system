package com.hardik.flenderson.service;

import org.springframework.stereotype.Service;

import com.hardik.flenderson.repository.EmployeeIssueRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeIssueService {
	
	private final EmployeeIssueRepository employeeIssueRepository;

}
