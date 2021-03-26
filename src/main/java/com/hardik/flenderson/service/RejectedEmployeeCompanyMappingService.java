package com.hardik.flenderson.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hardik.flenderson.entity.Employee;
import com.hardik.flenderson.enums.ExceptionMessage;
import com.hardik.flenderson.exception.InvalidManagerIdException;
import com.hardik.flenderson.repository.ManagerRepository;
import com.hardik.flenderson.repository.RejectedEmployeeCompanyMappingRepository;
import com.hardik.flenderson.request.RejectedEmployeeReversalRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RejectedEmployeeCompanyMappingService {

	private final RejectedEmployeeCompanyMappingRepository rejectedEmployeeCompanyMappingRepository;

	private final ManagerRepository managerRepository;

	public List<Employee> retreive(UUID managerId) {
		final var manager = managerRepository.findById(managerId)
				.orElseThrow(() -> new InvalidManagerIdException(ExceptionMessage.INVALID_MANAGER_ID.getMessage()));
		return rejectedEmployeeCompanyMappingRepository.findByCompanyId(manager.getCompanyId()).parallelStream()
				.map(rejectedEmployeeCompanyMapping -> rejectedEmployeeCompanyMapping.getEmployee())
				.collect(Collectors.toList());
	}

	public void undo(RejectedEmployeeReversalRequest rejectedEmployeeReversalRequest, UUID managerId) {
		final var manager = managerRepository.findById(managerId)
				.orElseThrow(() -> new InvalidManagerIdException(ExceptionMessage.INVALID_MANAGER_ID.getMessage()));
		final var company = manager.getCompany();
		rejectedEmployeeCompanyMappingRepository.deleteById(rejectedEmployeeCompanyMappingRepository
				.findByEmployeeIdAndCompanyId(rejectedEmployeeReversalRequest.getEmployeeId(), company.getId()).get()
				.getId());
	}

	public void inspectExpiration() {
		rejectedEmployeeCompanyMappingRepository.findAll().parallelStream()
				.filter(rejectedEmployeeCompanyMapping -> rejectedEmployeeCompanyMapping.getIsActive().equals(true))
				.collect(Collectors.toList()).stream().forEach(rejectedEmployeeCompanyMapping -> {
					if (rejectedEmployeeCompanyMapping.getCreatedAt().plusMonths(3).isBefore(LocalDateTime.now())) {
						rejectedEmployeeCompanyMapping.setIsActive(false);
						rejectedEmployeeCompanyMappingRepository.save(rejectedEmployeeCompanyMapping);
					}

				});
		;
	}

}
