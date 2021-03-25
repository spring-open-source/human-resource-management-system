package com.hardik.flenderson.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hardik.flenderson.entity.EmployeeRole;
import com.hardik.flenderson.entity.MasterRole;
import com.hardik.flenderson.enums.ExceptionMessage;
import com.hardik.flenderson.exception.InvalidEmployeeIdException;
import com.hardik.flenderson.exception.InvalidEmployeeRoleIdException;
import com.hardik.flenderson.exception.InvalidMasterRoleIdException;
import com.hardik.flenderson.repository.EmployeeRepository;
import com.hardik.flenderson.repository.EmployeeRoleRepository;
import com.hardik.flenderson.repository.MasterRoleRepository;
import com.hardik.flenderson.request.EmployeeRoleCreationRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeRoleService {

	private final EmployeeRoleRepository employeeRoleRepository;

	private final EmployeeRepository employeeRepository;

	private final MasterRoleRepository masterRoleRepository;

	public List<MasterRole> getEmployeeRoles(UUID employeeId) {
		return employeeRoleRepository.findByEmployeeId(employeeId).stream()
				.map(employeeRole -> employeeRole.getMasterRole()).collect(Collectors.toList());
	}

	public void create(EmployeeRoleCreationRequest employeeRoleCreationRequest) {
		final var employee = employeeRepository.findById(employeeRoleCreationRequest.getEmployeeId())
				.orElseThrow(() -> new InvalidEmployeeIdException(ExceptionMessage.INVALID_EMPLOYEE_ID.getMessage()));
		final MasterRole masterRole = employeeRoleCreationRequest.getRoleId() != null
				? masterRoleRepository.findById(employeeRoleCreationRequest.getRoleId()).orElseThrow(
						() -> new InvalidMasterRoleIdException(ExceptionMessage.INVALID_MASTER_ROLE_ID.getMessage()))
				: masterRoleRepository.findByNameIgnoreCase(employeeRoleCreationRequest.getName()).orElseGet(() -> {
					final var newMasterRole = new MasterRole();
					newMasterRole.setName(employeeRoleCreationRequest.getName());
					return masterRoleRepository.save(newMasterRole);
				});
		final var savedMasterRole = masterRoleRepository.save(masterRole);
		final var employeeRole = new EmployeeRole();
		employeeRole.setEmployeeId(employee.getId());
		employeeRole.setRoleId(savedMasterRole.getId());
		employeeRoleRepository.save(employeeRole);
	}

	public void deleteRole(UUID employeeRoleId) {
		employeeRoleRepository.delete(employeeRoleRepository.findById(employeeRoleId).orElseThrow(
				() -> new InvalidEmployeeRoleIdException(ExceptionMessage.INVALID_EMPLOYEE_ROLE_ID.getMessage())));
	}

}
