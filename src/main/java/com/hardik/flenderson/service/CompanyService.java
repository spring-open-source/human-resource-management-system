package com.hardik.flenderson.service;

import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hardik.flenderson.entity.Company;
import com.hardik.flenderson.enums.ExceptionMessage;
import com.hardik.flenderson.exception.InvalidManagerIdException;
import com.hardik.flenderson.mailing.dto.CompanyCreationDto;
import com.hardik.flenderson.mailing.event.CompanyCreationEvent;
import com.hardik.flenderson.repository.CompanyRepository;
import com.hardik.flenderson.repository.ManagerRepository;
import com.hardik.flenderson.request.CompanyCreationRequest;
import com.hardik.flenderson.storage.StorageService;
import com.hardik.flenderson.utility.CompanyCodeUtility;
import com.hardik.flenderson.utility.S3KeyUtility;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CompanyService {

	private final CompanyRepository companyRepository;

	private final ManagerRepository managerRepository;

	private final StorageService storageService;

	private final ApplicationEventPublisher applicationEventPublisher;

	public void create(CompanyCreationRequest companyCreationRequest, UUID managerId, MultipartFile companyLogo) {
		final var company = new Company();
		final var manager = managerRepository.findById(managerId)
				.orElseThrow(() -> new InvalidManagerIdException(ExceptionMessage.INVALID_MANAGER_ID.getMessage()));
		company.setCompanyCode(CompanyCodeUtility.generateCode());
		company.setName(companyCreationRequest.getName());

		if (companyLogo != null && !companyLogo.isEmpty()) {
			final var companyLogoKey = S3KeyUtility.getCompanyLogoKey(manager, companyCreationRequest.getName());
			storageService.save(companyLogoKey, companyLogo);
			company.setLogoUrl(companyLogoKey);
		}

		final var savedCompany = companyRepository.save(company);
		manager.setCompany(savedCompany);
		managerRepository.save(manager);
		applicationEventPublisher.publishEvent(new CompanyCreationEvent(CompanyCreationDto.builder()
				.companyCode(savedCompany.getCompanyCode()).companyName(savedCompany.getName())
				.email(manager.getEmailId()).firstName(manager.getFirstName()).build()));
	}

}
