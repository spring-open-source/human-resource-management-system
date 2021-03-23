package com.hardik.flenderson.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hardik.flenderson.dto.EmployeeDetailDto;
import com.hardik.flenderson.interceptor.AuthenticationInterceptor;
import com.hardik.flenderson.request.EmployeeDetailUpdationRequest;
import com.hardik.flenderson.request.EmployeeIssueCreationRequest;
import com.hardik.flenderson.service.EmployeeIssueService;
import com.hardik.flenderson.service.EmployeeService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class EmployeeController extends AuthenticationInterceptor {

	private final EmployeeService employeeService;

	private final EmployeeIssueService employeeIssueService;

	@GetMapping("v1/employee/{employeeId}")
	public EmployeeDetailDto employeeRetreivalHandler(
			@PathVariable(name = "employeeId", required = true) final UUID employeeId) {
		return employeeService.retreive(employeeId);
	}

	@PutMapping("v1/employee-details")
	public void employeeDetailsUpdationHandler(
			@RequestBody(required = true) final EmployeeDetailUpdationRequest employeeDetailUpdationRequest) {
		employeeService.updateDetails(employeeDetailUpdationRequest);
	}

	@PutMapping("v1/employee-details/profile-picture")
	public void employeeProfilePictureUpdationHandler(
			@RequestPart(name = "image", required = true) final MultipartFile multipartFile) {
		employeeService.updateProfilePicture(getUserDetails().getUserId(), multipartFile);
	}

	@PostMapping("v1/employee-issue")
	public void employeeIssueCreationRequest(
			@RequestBody(required = true) final EmployeeIssueCreationRequest employeeIssueCreationRequest) {
		employeeIssueService.create(employeeIssueCreationRequest, getUserDetails().getUserId());
	}
}
