package com.hardik.flenderson.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hardik.flenderson.dto.ManagerDetailDto;
import com.hardik.flenderson.entity.ManagerSocial;
import com.hardik.flenderson.interceptor.AuthenticationInterceptor;
import com.hardik.flenderson.request.AcceptCompanyJoinRequest;
import com.hardik.flenderson.request.EmployeeIssueResponseCreationRequest;
import com.hardik.flenderson.request.ManagerDetailUpdationRequest;
import com.hardik.flenderson.request.ManagerSocialCreationRequest;
import com.hardik.flenderson.request.ManagerSocialUpdationRequest;
import com.hardik.flenderson.request.RejectCompanyJoinRequest;
import com.hardik.flenderson.request.RemoveEmployeeFromCompanyRequest;
import com.hardik.flenderson.service.EmployeeIssueService;
import com.hardik.flenderson.service.ManagerService;
import com.hardik.flenderson.service.ManagerSocialService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ManagerController extends AuthenticationInterceptor {

	private final ManagerService managerService;

	private final EmployeeIssueService employeeIssueService;

	private final ManagerSocialService managerSocialService;

	@GetMapping("v1/manager/{managerId}")
	public ManagerDetailDto employeeRetreivalHandler(
			@PathVariable(name = "managerId", required = true) final UUID managerId) {
		return managerService.retreiveManager(managerId);
	}

	@PutMapping("v1/manager-details")
	public void managerDetailsUpdationHandler(
			@RequestBody(required = true) final ManagerDetailUpdationRequest managerDetailUpdationRequest) {
		managerService.updateDetails(managerDetailUpdationRequest);
	}

	@PutMapping("v1/manager-details/profile-picture")
	public void managerProfilePictureUpdationHandler(
			@RequestPart(name = "image", required = true) final MultipartFile multipartFile) {
		managerService.updateProfilePicture(getUserDetails().getUserId(), multipartFile);
	}

	@PostMapping("v1/accept-join-request")
	public void acceptCompanyJoinRequest(
			@RequestBody(required = true) final AcceptCompanyJoinRequest acceptCompanyJoinRequest) {
		managerService.acceptCompanyJoinRequest(acceptCompanyJoinRequest, getUserDetails().getUserId());
	}

	@PostMapping("v1/reject-join-request")
	public void rejectCompanyJoinRequest(
			@RequestBody(required = true) final RejectCompanyJoinRequest rejectCompanyJoinRequest) {
		managerService.rejectCompanyJoinRequest(rejectCompanyJoinRequest, getUserDetails().getUserId());
	}

	@PostMapping("v1/remove-from-company")
	public void removeEmployeeFromCompanyHandler(
			@RequestBody(required = true) final RemoveEmployeeFromCompanyRequest removeEmployeeFromCompanyRequest) {
		managerService.removeEmployeeFromCompany(removeEmployeeFromCompanyRequest, getUserDetails().getUserId());
	}

	@PostMapping("v1/employee-issue-response")
	public void respondToEmployeeIssue(
			@RequestBody(required = true) final EmployeeIssueResponseCreationRequest employeeIssueResponseCreationRequest) {
		employeeIssueService.respond(employeeIssueResponseCreationRequest);
	}

	@GetMapping("v1/manager-socials/{managerId}")
	public List<ManagerSocial> retreiveManagerSocials(
			@PathVariable(name = "managerId", required = true) final UUID managerId) {
		return managerSocialService.retreive(managerId);
	}

	@PostMapping("v1/manager-social")
	public void managerSocialCreationHandler(
			@RequestBody(required = true) final ManagerSocialCreationRequest managerSocialCreationRequest) {
		managerSocialService.create(managerSocialCreationRequest, getUserDetails().getUserId());
	}

	@PutMapping("v1/manager-social")
	public void managerSocialUpdationHandler(
			@RequestBody(required = true) final ManagerSocialUpdationRequest managerSocialUpdationRequest) {
		managerSocialService.update(managerSocialUpdationRequest);
	}

	@DeleteMapping("v1/manager-social/{managerSocialId}")
	public void managerSocialDeletionRequest(
			@PathVariable(name = "managerSocialId", required = true) final UUID managerSocialId) {
		managerSocialService.delete(managerSocialId);
	}
}
