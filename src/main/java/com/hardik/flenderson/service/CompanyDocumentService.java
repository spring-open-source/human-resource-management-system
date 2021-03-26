package com.hardik.flenderson.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hardik.flenderson.entity.CompanyDocument;
import com.hardik.flenderson.enums.ExceptionMessage;
import com.hardik.flenderson.exception.InvalidManagerIdException;
import com.hardik.flenderson.repository.CompanyDocumentRepository;
import com.hardik.flenderson.repository.ManagerRepository;
import com.hardik.flenderson.request.CompanyDocumentCreationRequest;
import com.hardik.flenderson.storage.StorageService;
import com.hardik.flenderson.utility.S3KeyUtility;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CompanyDocumentService {

	private final CompanyDocumentRepository companyDocumentRepository;

	private final ManagerRepository managerRepository;

	private final StorageService storageService;

	public void create(CompanyDocumentCreationRequest companyDocumentCreationRequest, UUID managerId,
			MultipartFile document) {
		final var companyDocument = new CompanyDocument();
		final var manager = managerRepository.findById(managerId)
				.orElseThrow(() -> new InvalidManagerIdException(ExceptionMessage.INVALID_MANAGER_ID.getMessage()));
		final var company = manager.getCompany();

		companyDocument.setCompanyId(company.getId());
		companyDocument.setName(companyDocumentCreationRequest.getName());

		if (document != null && !document.isEmpty()) {
			String documentKey = S3KeyUtility.getCompanyDocumentKey(manager, company.getName());
			storageService.save(documentKey, document);
			companyDocument.setDocumentUrl(documentKey);
		}

		final var savedCompanyDocument = companyDocumentRepository.save(companyDocument);

	}

	public List<CompanyDocument> retreive(UUID companyId) {
		return companyDocumentRepository.findByCompanyId(companyId);
	}

}
