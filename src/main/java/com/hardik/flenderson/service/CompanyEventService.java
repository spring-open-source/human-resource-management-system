package com.hardik.flenderson.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hardik.flenderson.entity.CompanyEvent;
import com.hardik.flenderson.enums.CompanyStatus;
import com.hardik.flenderson.enums.ExceptionMessage;
import com.hardik.flenderson.exception.InvalidManagerIdException;
import com.hardik.flenderson.mailing.event.EventCreationEvent;
import com.hardik.flenderson.repository.CompanyEventRepository;
import com.hardik.flenderson.repository.EmployeeRepository;
import com.hardik.flenderson.repository.ManagerRepository;
import com.hardik.flenderson.request.CompanyEventCreationRequest;
import com.hardik.flenderson.storage.StorageService;
import com.hardik.flenderson.utility.S3KeyUtility;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CompanyEventService {

	private final CompanyEventRepository companyEventRepository;

	private final ManagerRepository managerRepository;

	private final EmployeeRepository employeeRepository;

	private final StorageService storageService;

	private final ApplicationEventPublisher applicationEventPublisher;

	public void create(CompanyEventCreationRequest companyEventCreationRequest, UUID managerId,
			MultipartFile eventImage) {
		final var companyEvent = new CompanyEvent();
		final var manager = managerRepository.findById(managerId)
				.orElseThrow(() -> new InvalidManagerIdException(ExceptionMessage.INVALID_MANAGER_ID.getMessage()));
		final var company = manager.getCompany();

		companyEvent.setCompanyId(company.getId());
		companyEvent.setIsActive(true);
		companyEvent.setHeading(companyEventCreationRequest.getHeading());
		companyEvent.setDescription(companyEventCreationRequest.getDescription());
		companyEvent.setDueDate(companyEventCreationRequest.getDueDate());

		if (eventImage != null && !eventImage.isEmpty()) {
			final var eventImageKey = S3KeyUtility.getCompanyEventImageKey(manager, company.getName(),
					companyEventCreationRequest.getHeading());
			storageService.save(eventImageKey, eventImage);
			companyEvent.setImageUrl(eventImageKey);
		}
		final var savedCompanyEvent = companyEventRepository.save(companyEvent);
		applicationEventPublisher.publishEvent(new EventCreationEvent(company.getEmployees().stream()
				.filter(employee -> employee.getCompanyStatus().equals(CompanyStatus.IN_COMPANY.getStatusId()))));

	}

	public void inspectForExpiredEvents() {
		companyEventRepository.findAll().forEach(companyEvent -> {
			if (LocalDate.now().isAfter(companyEvent.getDueDate())) {
				companyEvent.setIsActive(false);
				companyEventRepository.save(companyEvent);
			}
		});
	}

	public List<CompanyEvent> retreive(UUID userId) {
		if (employeeRepository.existsById(userId)) {
			final var employee = employeeRepository.findById(userId).get();
			return employee.getCompany().getCompanyEvents().parallelStream().collect(Collectors.toList());
		} else {
			final var manager = managerRepository.findById(userId).get();
			return manager.getCompany().getCompanyEvents().parallelStream().collect(Collectors.toList());
		}
	}

}
