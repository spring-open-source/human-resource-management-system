package com.hardik.flenderson.service;

import java.io.IOException;
import java.util.UUID;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hardik.flenderson.dto.EmployeeDetailDto;
import com.hardik.flenderson.entity.Employee;
import com.hardik.flenderson.entity.Manager;
import com.hardik.flenderson.enums.CompanyStatus;
import com.hardik.flenderson.enums.ExceptionMessage;
import com.hardik.flenderson.exception.EmptyEmployeeProfilePictureException;
import com.hardik.flenderson.exception.InvalidCompanyCodeAndNameException;
import com.hardik.flenderson.exception.InvalidEmployeeEmailIdException;
import com.hardik.flenderson.exception.InvalidEmployeeIdException;
import com.hardik.flenderson.exception.RejectedEmployeeBeingDesperateException;
import com.hardik.flenderson.keycloak.dto.KeycloakUserDto;
import com.hardik.flenderson.mailing.dto.CompanyJoinRequestRecievedDto;
import com.hardik.flenderson.mailing.dto.CompanyJoinRequestSentDto;
import com.hardik.flenderson.mailing.dto.SimpleEmailDto;
import com.hardik.flenderson.mailing.event.CompanyJoiningRequestRecievedEvent;
import com.hardik.flenderson.mailing.event.CompanyJoiningRequestSentEvent;
import com.hardik.flenderson.mailing.event.EmployeeAccountCreationEvent;
import com.hardik.flenderson.repository.CompanyRepository;
import com.hardik.flenderson.repository.EmployeeRepository;
import com.hardik.flenderson.repository.RejectedEmployeeCompanyMappingRepository;
import com.hardik.flenderson.request.CompanyJoinRequest;
import com.hardik.flenderson.request.EmployeeDetailUpdationRequest;
import com.hardik.flenderson.storage.StorageService;
import com.hardik.flenderson.utility.S3KeyUtility;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class EmployeeService {

	private final EmployeeRepository employeeRepository;

	private final StorageService storageService;

	private final CompanyRepository companyRepository;

	private final RejectedEmployeeCompanyMappingRepository rejectedEmployeeCompanyMappingRepository;

	private final ApplicationEventPublisher applicationEventPublisher;

	public Employee getEmployeeFromKeycloakUserHandler(KeycloakUserDto keyCloakUser) {
		if (employeeRepository.existsByEmailIdIgnoreCase(keyCloakUser.getEmail()))
			return employeeRepository.findByEmailIdIgnoreCase(keyCloakUser.getEmail()).orElseThrow(
					() -> new InvalidEmployeeEmailIdException(ExceptionMessage.INVALID_EMPLOYEE_EMAIL.getMessage()));
		else {
			final var employee = new Employee();
			employee.setFirstName(keyCloakUser.getFirstName());
			employee.setLastName(keyCloakUser.getLastName());
			employee.setEmailId(keyCloakUser.getEmail());
			applicationEventPublisher.publishEvent(
					new EmployeeAccountCreationEvent(SimpleEmailDto.builder().email(keyCloakUser.getEmail()).build()));
			return employeeRepository.save(employee);
		}

	}

	public void updateDetails(EmployeeDetailUpdationRequest employeeDetailUpdationRequest) {
		final var employee = employeeRepository.findById(employeeDetailUpdationRequest.getEmployeeId())
				.orElseThrow(() -> new InvalidEmployeeIdException(ExceptionMessage.INVALID_EMPLOYEE_ID.getMessage()));
		employee.setFirstName(employeeDetailUpdationRequest.getFirstName());
		employee.setMiddleName(employeeDetailUpdationRequest.getMiddleName());
		employee.setLastName(employeeDetailUpdationRequest.getLastName());
		employee.setDescription(employeeDetailUpdationRequest.getDescription());
		employee.setStatus(employeeDetailUpdationRequest.getStatus());
		employee.setGender(employeeDetailUpdationRequest.getGender());
		employee.setDateOfBirth(employeeDetailUpdationRequest.getDateOfBirth());
		employee.setCountryId(employeeDetailUpdationRequest.getCountryId());
		employeeRepository.save(employee);
	}

	public void updateProfilePicture(UUID userId, MultipartFile multipartFile) {
		if (multipartFile.isEmpty() || multipartFile == null)
			throw new EmptyEmployeeProfilePictureException(
					ExceptionMessage.EMPTY_EMPLOYEE_PROFILE_PICTURE.getMessage());
		final var employee = employeeRepository.findById(userId)
				.orElseThrow(() -> new InvalidEmployeeIdException(ExceptionMessage.INVALID_EMPLOYEE_ID.getMessage()));
		String keyForEmployeeProfilePicture = S3KeyUtility.getProfilePictureKey(employee);
		storageService.save(keyForEmployeeProfilePicture, multipartFile);
		employee.setImageUrl(keyForEmployeeProfilePicture);
		employeeRepository.save(employee);
	}

	public EmployeeDetailDto retreive(UUID employeeId) {
		final var employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new InvalidEmployeeIdException(ExceptionMessage.INVALID_EMPLOYEE_ID.getMessage()));
		String profilePicture = null;
		if (employee.getImageUrl() != null)
			try {
				profilePicture = Base64.encodeBase64String(
						storageService.getFile(employee.getImageUrl()).getObjectContent().readAllBytes());
			} catch (IOException e) {
				log.error("UNABLE TO RETREIVE S3 IMAGE WITH KEY " + employee.getImageUrl());
			}
		return EmployeeDetailDto.builder().dateOfBirth(employee.getDateOfBirth()).description(employee.getDescription())
				.emailId(employee.getEmailId()).firstName(employee.getFirstName()).gender(employee.getGender())
				.profilePicture(profilePicture).lastName(employee.getLastName()).middleName(employee.getMiddleName())
				.status(employee.getStatus()).profileCompleted(employee.getGender() == null ? false : true)
				.companyStatus(employee.getCompanyStatus()).countryId(employee.getCountryId()).build();
	}

	public void joinCompanyRequest(CompanyJoinRequest companyJoinRequest, UUID employeeId) {
		final var employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new InvalidEmployeeIdException(ExceptionMessage.INVALID_EMPLOYEE_ID.getMessage()));
		final var company = companyRepository
				.findByNameAndCompanyCode(companyJoinRequest.getCompanyName(), companyJoinRequest.getCompanyCode())
				.orElseThrow(() -> new InvalidCompanyCodeAndNameException(
						ExceptionMessage.INVALID_COMPANY_CODE_AND_NAME.getMessage()));
		final var manager = company.getManagers().stream().findAny().get();
		if (rejectedEmployeeCompanyMappingRepository.findByEmployeeIdAndCompanyId(employee.getId(), company.getId())
				.isPresent())
			throw new RejectedEmployeeBeingDesperateException(
					ExceptionMessage.REJECTED_EMPLOYEE_BEING_DESPERATE.getMessage());
		employee.setCompanyStatus(CompanyStatus.REQUEST_SENT.getStatusId());
		employee.setCompanyId(company.getId());
		final var savedEmployee = employeeRepository.save(employee);
		applicationEventPublisher.publishEvent(
				new CompanyJoiningRequestSentEvent(CompanyJoinRequestSentDto.builder().companyName(company.getName())
						.email(savedEmployee.getEmailId()).firstName(savedEmployee.getFirstName()).build()));
		applicationEventPublisher.publishEvent(new CompanyJoiningRequestRecievedEvent(
				CompanyJoinRequestRecievedDto.builder().companyName(company.getName()).email(manager.getEmailId())
						.employeeEmail(savedEmployee.getEmailId())
						.employeeName(savedEmployee.getFirstName() + " " + savedEmployee.getLastName())
						.firstName(manager.getFirstName()).build()));
	}

	public void retractCompanyJoinRequest(UUID employeeId) {
		final var employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new InvalidEmployeeIdException(ExceptionMessage.INVALID_EMPLOYEE_ID.getMessage()));
		employee.setCompanyStatus(CompanyStatus.IN_NO_COMPANY.getStatusId());
		employee.setCompany(null);
		employee.setCompanyId(null);
		employeeRepository.save(employee);
	}

}
