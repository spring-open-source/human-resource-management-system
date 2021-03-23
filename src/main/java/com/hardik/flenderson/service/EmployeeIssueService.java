package com.hardik.flenderson.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.hardik.flenderson.entity.EmployeeIssue;
import com.hardik.flenderson.repository.EmployeeIssueRepository;
import com.hardik.flenderson.repository.EmployeeRepository;
import com.hardik.flenderson.request.EmployeeIssueCreationRequest;
import com.hardik.flenderson.utility.EmployeeIssueUtility;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeIssueService {

	private final EmployeeIssueRepository employeeIssueRepository;

	private final EmployeeRepository employeeRepository;

	public void create(EmployeeIssueCreationRequest employeeIssueCreationRequest, UUID employeeId) {
		final var employeeIssue = new EmployeeIssue();
		final var employee = employeeRepository.findById(employeeId).get();
		final var company = employee.getCompany();

		employeeIssue.setCompanyId(company.getId());
		employeeIssue.setTitle(employeeIssueCreationRequest.getTitle());
		employeeIssue.setDescription(employeeIssueCreationRequest.getDescription());
		employeeIssue.setEmployeeId(employeeId);
		employeeIssue.setIsActive(true);
		employeeIssue.setIssueType(EmployeeIssueUtility.getIssueType(employeeIssueCreationRequest.getIssueType()));
		
		final var savedEmployeeIssue = employeeIssueRepository.save(employeeIssue);
	}

}
