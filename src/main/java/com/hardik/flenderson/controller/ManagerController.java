package com.hardik.flenderson.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hardik.flenderson.dto.ManagerDetailDto;
import com.hardik.flenderson.interceptor.AuthenticationInterceptor;
import com.hardik.flenderson.request.ManagerDetailUpdationRequest;
import com.hardik.flenderson.service.ManagerService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ManagerController extends AuthenticationInterceptor {

	private final ManagerService managerService;

	@GetMapping("v1/manager/{managerId}")
	public ManagerDetailDto employeeRetreivalHandler(@PathVariable(name = "managerId", required = true) final UUID managerId) {
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

}
