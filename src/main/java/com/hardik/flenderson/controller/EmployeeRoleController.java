package com.hardik.flenderson.controller;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hardik.flenderson.entity.MasterRole;
import com.hardik.flenderson.request.EmployeeRoleCreationRequest;
import com.hardik.flenderson.service.EmployeeRoleService;
import com.hardik.flenderson.service.MasterRoleService;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.DELETE,
		RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.OPTIONS, RequestMethod.PATCH, RequestMethod.POST,
		RequestMethod.PUT, RequestMethod.TRACE })
public class EmployeeRoleController {

	private final MasterRoleService masterRoleService;

	private final EmployeeRoleService employeeRoleService;

	@GetMapping("v1/master-roles")
	public List<MasterRole> masterRolesReteivalHandler() {
		return masterRoleService.getMasterRoles();
	}

	@GetMapping("v1/employee-roles/{employeeId}")
	public List<MasterRole> employeeRolesRetreivalHandler(
			@PathVariable(name = "employeeId", required = true) final UUID employeeId) {
		return employeeRoleService.getEmployeeRoles(employeeId);
	}

	@PostMapping("v1/employee-roles")
	public void employeeRoleCreationHandler(
			@RequestBody(required = true) final EmployeeRoleCreationRequest employeeRoleCreationRequest) {
		employeeRoleService.create(employeeRoleCreationRequest);
	}

	@DeleteMapping("v1/employee-roles/{employeeRoleId}")
	public void employeeRoleDeletionHandler(@PathVariable(name = "employeeRoleId", required = true)final UUID employeeRoleId) {
		employeeRoleService.deleteRole(employeeRoleId);
	}
}
