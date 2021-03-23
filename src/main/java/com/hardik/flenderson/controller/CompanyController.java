package com.hardik.flenderson.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.hardik.flenderson.interceptor.AuthenticationInterceptor;
import com.hardik.flenderson.request.CompanyCreationRequest;
import com.hardik.flenderson.request.CompanyDocumentCreationRequest;
import com.hardik.flenderson.request.CompanyEventCreationRequest;
import com.hardik.flenderson.service.CompanyDocumentService;
import com.hardik.flenderson.service.CompanyEventService;
import com.hardik.flenderson.service.CompanyService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class CompanyController extends AuthenticationInterceptor {

	private final CompanyService companyService;

	private final CompanyDocumentService companyDocumentService;

	private final CompanyEventService companyEventService;

	@PostMapping("v1/company")
	public void companyCreationHandler(@RequestPart(name = "file", required = false) MultipartFile multipartFile,
			@RequestPart(name = "data", required = true) final String companyCreationRequest)
			throws JsonMappingException, JsonProcessingException {
		CompanyCreationRequest parsedCompanyCreationRequest = new ObjectMapper()
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
				.registerModule(new ParameterNamesModule()).registerModule(new Jdk8Module())
				.registerModule(new JavaTimeModule()).readValue(companyCreationRequest, CompanyCreationRequest.class);
		companyService.create(parsedCompanyCreationRequest, getUserDetails().getUserId(), multipartFile);
	}

	@PostMapping("v1/company-document")
	public void companyDocumentCreationHandler(@RequestPart(name = "file", required = true) MultipartFile document,
			@RequestPart(name = "data", required = true) final String companyDocumentCreationRequest)
			throws JsonMappingException, JsonProcessingException {
		CompanyDocumentCreationRequest parsedCompanyDocumentCreationRequest = new ObjectMapper()
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
				.registerModule(new ParameterNamesModule()).registerModule(new Jdk8Module())
				.registerModule(new JavaTimeModule())
				.readValue(companyDocumentCreationRequest, CompanyDocumentCreationRequest.class);
		companyDocumentService.create(parsedCompanyDocumentCreationRequest, getUserDetails().getUserId(), document);
	}

	@PostMapping("v1/company-event")
	public void companyEventCreationHandler(@RequestPart(name = "file", required = true) MultipartFile eventImage,
			@RequestPart(name = "data", required = true) final String companyEventCreationRequest)
			throws JsonMappingException, JsonProcessingException {
		CompanyEventCreationRequest parsedCompanyEventCreationRequest = new ObjectMapper()
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
				.registerModule(new ParameterNamesModule()).registerModule(new Jdk8Module())
				.registerModule(new JavaTimeModule())
				.readValue(companyEventCreationRequest, CompanyEventCreationRequest.class);
		companyEventService.create(parsedCompanyEventCreationRequest, getUserDetails().getUserId(), eventImage);
	}

}
