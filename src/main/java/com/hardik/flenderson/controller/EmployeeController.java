package com.hardik.flenderson.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hardik.flenderson.dto.EmployeeDetailDto;
import com.hardik.flenderson.entity.EmployeeSocial;
import com.hardik.flenderson.interceptor.AuthenticationInterceptor;
import com.hardik.flenderson.request.CompanyJoinRequest;
import com.hardik.flenderson.request.EmployeeDetailUpdationRequest;
import com.hardik.flenderson.request.EmployeeIssueCreationRequest;
import com.hardik.flenderson.request.EmployeeSocialCreationRequest;
import com.hardik.flenderson.request.EmployeeSocialUpdationRequest;
import com.hardik.flenderson.service.EmployeeIssueService;
import com.hardik.flenderson.service.EmployeeService;
import com.hardik.flenderson.service.EmployeeSocialService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.DELETE,
		RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.OPTIONS, RequestMethod.PATCH, RequestMethod.POST,
		RequestMethod.PUT, RequestMethod.TRACE })
public class EmployeeController extends AuthenticationInterceptor {

	private final EmployeeService employeeService;

	private final EmployeeIssueService employeeIssueService;

	private final EmployeeSocialService employeeSocialService;

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

	@PostMapping("v1/join-company")
	public void companyJoinRequestHandler(@RequestBody(required = true) final CompanyJoinRequest companyJoinRequest) {
		employeeService.joinCompanyRequest(companyJoinRequest, getUserDetails().getUserId());
	}

	@PostMapping("v1/retract-join-company-request")
	public void retractCompanyJoinRequestHandler() {
		employeeService.retractCompanyJoinRequest(getUserDetails().getUserId());
	}

	@GetMapping("v1/employee-social/employeeId")
	public List<EmployeeSocial> retreiveEmployeeSocials(
			@PathVariable(name = "employeeId", required = true) final UUID employeeId) {
		return employeeSocialService.retreive(employeeId);
	}

	@PostMapping("v1/employee-social")
	public void employeeSocialCreationHandler(
			@RequestBody(required = true) final EmployeeSocialCreationRequest employeeSocialCreationRequest) {
		employeeSocialService.create(employeeSocialCreationRequest, getUserDetails().getUserId());
	}

	@PutMapping("v1/employee-social")
	public void employeeSocialUpdationHandler(
			@RequestBody(required = true) final EmployeeSocialUpdationRequest employeeSocialUpdationRequest) {
		employeeSocialService.update(employeeSocialUpdationRequest);
	}

	@DeleteMapping("v1/employee-social/{employeeSocialId}")
	public void employeeSocialDeletionRequest(
			@PathVariable(name = "employeeSocialId", required = true) final UUID employeeSocialId) {
		employeeSocialService.delete(employeeSocialId);
	}

}
