package com.hardik.flenderson.service;

import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.hardik.flenderson.entity.EmployeeIssue;
import com.hardik.flenderson.enums.ExceptionMessage;
import com.hardik.flenderson.exception.InvalidEmployeeIdException;
import com.hardik.flenderson.exception.InvalidEmployeeIssueIdException;
import com.hardik.flenderson.mailing.dto.IssueRaisedSuccessfullyDto;
import com.hardik.flenderson.mailing.dto.IssueRecievedDto;
import com.hardik.flenderson.mailing.dto.IssueResponseRecievedDto;
import com.hardik.flenderson.mailing.event.IssueRaisedEvent;
import com.hardik.flenderson.mailing.event.IssueRecievedEvent;
import com.hardik.flenderson.mailing.event.IssueResponseEvent;
import com.hardik.flenderson.repository.EmployeeIssueRepository;
import com.hardik.flenderson.repository.EmployeeRepository;
import com.hardik.flenderson.request.EmployeeIssueCreationRequest;
import com.hardik.flenderson.request.EmployeeIssueResponseCreationRequest;
import com.hardik.flenderson.utility.EmployeeIssueUtility;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeIssueService {

	private final EmployeeIssueRepository employeeIssueRepository;

	private final EmployeeRepository employeeRepository;

	private final ApplicationEventPublisher applicationEventPublisher;

	public void create(EmployeeIssueCreationRequest employeeIssueCreationRequest, UUID employeeId) {
		final var employeeIssue = new EmployeeIssue();
		final var employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new InvalidEmployeeIdException(ExceptionMessage.INVALID_EMPLOYEE_ID.getMessage()));
		final var company = employee.getCompany();
		final var manager = company.getManagers().stream().findFirst().get();

		employeeIssue.setCompanyId(company.getId());
		employeeIssue.setTitle(employeeIssueCreationRequest.getTitle());
		employeeIssue.setDescription(employeeIssueCreationRequest.getDescription());
		employeeIssue.setEmployeeId(employeeId);
		employeeIssue.setIsActive(true);
		employeeIssue.setIssueType(EmployeeIssueUtility.getIssueType(employeeIssueCreationRequest.getIssueType()));

		final var savedEmployeeIssue = employeeIssueRepository.save(employeeIssue);

		applicationEventPublisher.publishEvent(new IssueRaisedEvent(IssueRaisedSuccessfullyDto.builder()
				.email(employee.getEmailId()).firstName(employee.getFirstName()).build()));

		applicationEventPublisher.publishEvent(new IssueRecievedEvent(
				IssueRecievedDto.builder().description(savedEmployeeIssue.getDescription()).email(manager.getEmailId())
						.employeeName(employee.getFirstName() + " " + employee.getLastName())
						.issueType(savedEmployeeIssue.getIssueType())
						.managerName(manager.getFirstName() + " " + manager.getLastName()).build()));
	}

	public void respond(EmployeeIssueResponseCreationRequest employeeIssueResponseCreationRequest) {
		final var employeeIssue = employeeIssueRepository.findById(employeeIssueResponseCreationRequest.getIssueId())
				.orElseThrow(() -> new InvalidEmployeeIssueIdException(
						ExceptionMessage.INVALID_EMPLOYEE_ISSUE_ID.getMessage()));
		final var employee = employeeIssue.getEmployee();
		employeeIssue.setResponse(employeeIssueResponseCreationRequest.getResponse());
		employeeIssue.setIsActive(false);
		employeeIssueRepository.save(employeeIssue);

		applicationEventPublisher.publishEvent(new IssueResponseEvent(IssueResponseRecievedDto.builder()
				.email(employee.getEmailId()).firstName(employee.getFirstName()).build()));
	}

}
