package com.hardik.flenderson.service;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hardik.flenderson.entity.CompanyEvent;
import com.hardik.flenderson.repository.CompanyEventRepository;
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

	private final StorageService storageService;

	public void create(CompanyEventCreationRequest companyEventCreationRequest, UUID managerId,
			MultipartFile eventImage) {
		final var companyEvent = new CompanyEvent();
		final var manager = managerRepository.findById(managerId).get();
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
	}

	public void inspectForExpiredEvents() {
		companyEventRepository.findAll().forEach(companyEvent -> {
			if (LocalDate.now().isAfter(companyEvent.getDueDate())) {
				companyEvent.setIsActive(false);
				companyEventRepository.save(companyEvent);
			}
		});
	}

}
