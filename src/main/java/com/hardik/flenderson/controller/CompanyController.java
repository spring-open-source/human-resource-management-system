package com.hardik.flenderson.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
import com.hardik.flenderson.entity.Company;
import com.hardik.flenderson.entity.CompanyDocument;
import com.hardik.flenderson.entity.CompanyEvent;
import com.hardik.flenderson.entity.CompanyJoinInvitation;
import com.hardik.flenderson.entity.CompanyReport;
import com.hardik.flenderson.entity.Employee;
import com.hardik.flenderson.entity.EmployeeIssue;
import com.hardik.flenderson.interceptor.AuthenticationInterceptor;
import com.hardik.flenderson.request.CompanyCreationRequest;
import com.hardik.flenderson.request.CompanyDocumentCreationRequest;
import com.hardik.flenderson.request.CompanyEventCreationRequest;
import com.hardik.flenderson.request.CompanyJoinInvitationCreationRequest;
import com.hardik.flenderson.request.RejectedEmployeeReversalRequest;
import com.hardik.flenderson.service.CompanyDocumentService;
import com.hardik.flenderson.service.CompanyEventService;
import com.hardik.flenderson.service.CompanyJoinInvitationService;
import com.hardik.flenderson.service.CompanyReportService;
import com.hardik.flenderson.service.CompanyService;
import com.hardik.flenderson.service.EmployeeIssueService;
import com.hardik.flenderson.service.RejectedEmployeeCompanyMappingService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class CompanyController extends AuthenticationInterceptor {

	private final CompanyService companyService;

	private final CompanyDocumentService companyDocumentService;

	private final CompanyEventService companyEventService;

	private final CompanyJoinInvitationService companyJoinInvitationService;

	private final EmployeeIssueService employeeIssueService;

	private final RejectedEmployeeCompanyMappingService rejectedEmployeeCompanyMappingService;

	private final CompanyReportService companyReportService;

	@GetMapping("v1/company")
	public Company retreiveCompanyHandler() {
		return companyService.retreive(getUserDetails().getUserId());
	}

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

	@GetMapping("v1/company-documents/{companyId}")
	public List<CompanyDocument> retreiveCompanyDocuments(
			@PathVariable(name = "companyId", required = true) final UUID companyId) {
		return companyDocumentService.retreive(companyId);
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

	@GetMapping("v1/company-events")
	public List<CompanyEvent> retreiveCompanyEventsHandler() {
		return companyEventService.retreive(getUserDetails().getUserId());
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

	@GetMapping("v1/company-invites")
	public List<CompanyJoinInvitation> getCompanyJoinInvitations() {
		return companyJoinInvitationService.retreiveInvitations(getUserDetails().getUserId());
	}

	@PostMapping("v1/company-invite")
	public void companyJoiningInvitationHandler(
			@RequestBody(required = true) final CompanyJoinInvitationCreationRequest companyJoinInvitationCreationRequest) {
		companyJoinInvitationService.sendInvite(companyJoinInvitationCreationRequest, getUserDetails().getUserId());
	}

	@GetMapping("v1/company-issue")
	public List<EmployeeIssue> reteiveIssuesRelatedToCompany() {
		return employeeIssueService.retreive(getUserDetails().getUserId());
	}

	@GetMapping("v1/company-employees")
	public List<Employee> retreiveCompanyEmployeesHandler() {
		return companyService.retreiveEmployees(getUserDetails().getUserId());
	}

	@GetMapping("v1/rejected-employees")
	public List<Employee> retreiveRejectedEmployees() {
		return rejectedEmployeeCompanyMappingService.retreive(getUserDetails().getUserId());
	}

	@PostMapping("v1/rejected-employees")
	public void unRejectEmployee(
			@RequestBody(required = true) final RejectedEmployeeReversalRequest rejectedEmployeeReversalRequest) {
		rejectedEmployeeCompanyMappingService.undo(rejectedEmployeeReversalRequest, getUserDetails().getUserId());
	}

	@GetMapping("v1/company-reports/{reportType}")
	public List<CompanyReport> companyReportsRetrievalHandler(
			@PathVariable(name = "reportType", required = true) final Integer reportType) {
		return companyReportService.retreive(reportType, getUserDetails().getUserId());
	}
}
