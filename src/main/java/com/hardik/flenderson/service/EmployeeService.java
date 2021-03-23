package com.hardik.flenderson.service;

import java.io.IOException;
import java.util.UUID;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hardik.flenderson.dto.EmployeeDetailDto;
import com.hardik.flenderson.entity.Employee;
import com.hardik.flenderson.enums.CompanyStatus;
import com.hardik.flenderson.keycloak.dto.KeycloakUserDto;
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

	public Employee getEmployee(KeycloakUserDto keyCloakUser) {
		if (employeeRepository.existsByEmailIdIgnoreCase(keyCloakUser.getEmail()))
			return employeeRepository.findByEmailIdIgnoreCase(keyCloakUser.getEmail()).get();
		else {
			final var employee = new Employee();
			employee.setFirstName(keyCloakUser.getFirstName());
			employee.setLastName(keyCloakUser.getLastName());
			employee.setEmailId(keyCloakUser.getEmail());
			return employeeRepository.save(employee);
		}

	}

	public void updateDetails(EmployeeDetailUpdationRequest employeeDetailUpdationRequest) {
		final var employee = employeeRepository.findById(employeeDetailUpdationRequest.getEmployeeId()).get();
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
			throw new RuntimeException();
		final var employee = employeeRepository.findById(userId).get();
		String keyForEmployeeProfilePicture = S3KeyUtility.getProfilePictureKey(employee);
		storageService.save(keyForEmployeeProfilePicture, multipartFile);
		employee.setImageUrl(keyForEmployeeProfilePicture);
		employeeRepository.save(employee);
	}

	public EmployeeDetailDto retreive(UUID employeeId) {
		final var employee = employeeRepository.findById(employeeId).get();
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
				.companyStatus(employee.getCompanyStatus()).build();
	}

	public void joinCompanyRequest(CompanyJoinRequest companyJoinRequest, UUID employeeId) {
		final var employee = employeeRepository.findById(employeeId).get();
		final var company = companyRepository
				.findByNameAndCompanyCode(companyJoinRequest.getCompanyName(), companyJoinRequest.getCompanyCode())
				.get();
		if (rejectedEmployeeCompanyMappingRepository.findByEmployeeIdAndCompanyId(employee.getId(), company.getId())
				.isPresent())
			throw new RuntimeException("");
		employee.setCompanyStatus(CompanyStatus.REQUEST_SENT.getStatusId());
		employee.setCompanyId(company.getId());
		employeeRepository.save(employee);
	}

	public void retractCompanyJoinRequest(UUID employeeId) {
		final var employee = employeeRepository.findById(employeeId).get();
		employee.setCompanyStatus(CompanyStatus.IN_NO_COMPANY.getStatusId());
		employee.setCompany(null);
		employee.setCompanyId(null);
		employeeRepository.save(employee);
	}

}
