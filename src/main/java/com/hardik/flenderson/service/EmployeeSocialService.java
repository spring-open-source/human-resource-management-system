package com.hardik.flenderson.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.hardik.flenderson.entity.EmployeeSocial;
import com.hardik.flenderson.enums.ExceptionMessage;
import com.hardik.flenderson.exception.EmployeeSocialWithNameAlreadyExistsException;
import com.hardik.flenderson.exception.InvalidEmployeeIdException;
import com.hardik.flenderson.exception.InvalidEmployeeSocialIdException;
import com.hardik.flenderson.repository.EmployeeRepository;
import com.hardik.flenderson.repository.EmployeeSocialRepository;
import com.hardik.flenderson.request.EmployeeSocialCreationRequest;
import com.hardik.flenderson.request.EmployeeSocialUpdationRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeSocialService {

	private final EmployeeSocialRepository employeeSocialRepository;

	private final EmployeeRepository employeeRepository;

	public void create(EmployeeSocialCreationRequest employeeSocialCreationRequest, UUID employeeId) {
		final var employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new InvalidEmployeeIdException(ExceptionMessage.INVALID_EMPLOYEE_ID.getMessage()));
		if (employeeSocialRepository.existsByEmployeeIdAndName(employee.getId(),
				employeeSocialCreationRequest.getName()))
			throw new EmployeeSocialWithNameAlreadyExistsException(
					ExceptionMessage.EMPLOYEE_SOCIAL_WITH_NAME_ALREADY_EXISTS.getMessage()
							.replace("--social-name--", employeeSocialCreationRequest.getName()));
		final var employeeSocial = new EmployeeSocial();
		employeeSocial.setName(employeeSocialCreationRequest.getName());
		employeeSocial.setUrl(employeeSocialCreationRequest.getUrl());
		employeeSocial.setEmployeeId(employeeId);
		employeeSocialRepository.save(employeeSocial);
	}

	public void update(EmployeeSocialUpdationRequest employeeSocialUpdationRequest) {
		final var employeeSocial = employeeSocialRepository
				.findById(employeeSocialUpdationRequest.getEmployeeSocialId())
				.orElseThrow(() -> new InvalidEmployeeSocialIdException(
						ExceptionMessage.INVALID_EMPLOYEE_SOCIAL_ID.getMessage()));
		employeeSocial.setUrl(employeeSocialUpdationRequest.getUrl());
		employeeSocialRepository.save(employeeSocial);
	}

	public void delete(UUID employeeSocialId) {
		final var employeeSocial = employeeSocialRepository.findById(employeeSocialId).orElseThrow(
				() -> new InvalidEmployeeSocialIdException(ExceptionMessage.INVALID_EMPLOYEE_SOCIAL_ID.getMessage()));
		employeeSocialRepository.delete(employeeSocial);
	}

	public List<EmployeeSocial> retreive(UUID employeeId) {
		return employeeSocialRepository.findByEmployeeId(employeeId);
	}

}
