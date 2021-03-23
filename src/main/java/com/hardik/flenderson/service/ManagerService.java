package com.hardik.flenderson.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hardik.flenderson.dto.ManagerDetailDto;
import com.hardik.flenderson.entity.EmployeeDailyAttendance;
import com.hardik.flenderson.entity.Manager;
import com.hardik.flenderson.entity.MonthlySalaryDetail;
import com.hardik.flenderson.entity.RejectedEmployeeCompanyMapping;
import com.hardik.flenderson.enums.CompanyStatus;
import com.hardik.flenderson.keycloak.dto.KeycloakUserDto;
import com.hardik.flenderson.repository.EmployeeDailyAttendanceRepository;
import com.hardik.flenderson.repository.EmployeeRepository;
import com.hardik.flenderson.repository.ManagerRepository;
import com.hardik.flenderson.repository.MonthlySalaryDetailRepository;
import com.hardik.flenderson.repository.RejectedEmployeeCompanyMappingRepository;
import com.hardik.flenderson.request.AcceptCompanyJoinRequest;
import com.hardik.flenderson.request.ManagerDetailUpdationRequest;
import com.hardik.flenderson.request.RejectCompanyJoinRequest;
import com.hardik.flenderson.request.RemoveEmployeeFromCompanyRequest;
import com.hardik.flenderson.storage.StorageService;
import com.hardik.flenderson.utility.S3KeyUtility;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class ManagerService {

	private final ManagerRepository managerRepository;

	private final EmployeeRepository employeeRepository;

	private final StorageService storageService;

	private final RejectedEmployeeCompanyMappingRepository rejectedEmployeeCompanyMappingRepository;

	private final EmployeeDailyAttendanceRepository employeeDailyAttendanceRepository;

	private final MonthlySalaryDetailRepository monthlySalaryDetailRepository;

	public Manager getManager(KeycloakUserDto keyCloakUser) {
		if (managerRepository.existsByEmailIdIgnoreCase(keyCloakUser.getEmail()))
			return managerRepository.findByEmailIdIgnoreCase(keyCloakUser.getEmail()).get();
		else {
			final var manager = new Manager();
			manager.setFirstName(keyCloakUser.getFirstName());
			manager.setLastName(keyCloakUser.getLastName());
			manager.setEmailId(keyCloakUser.getEmail());
			return managerRepository.save(manager);
		}
	}

	public void updateDetails(ManagerDetailUpdationRequest managerDetailUpdationRequest) {
		final var manager = managerRepository.findById(managerDetailUpdationRequest.getManagerId()).get();
		manager.setFirstName(managerDetailUpdationRequest.getFirstName());
		manager.setMiddleName(managerDetailUpdationRequest.getMiddleName());
		manager.setLastName(managerDetailUpdationRequest.getLastName());
		manager.setDescription(managerDetailUpdationRequest.getDescription());
		manager.setStatus(managerDetailUpdationRequest.getStatus());
		manager.setGender(managerDetailUpdationRequest.getGender());
		manager.setDateOfBirth(managerDetailUpdationRequest.getDateOfBirth());
		manager.setCountryId(managerDetailUpdationRequest.getCountryId());
		managerRepository.save(manager);
		managerRepository.save(manager);
	}

	public void updateProfilePicture(UUID userId, MultipartFile multipartFile) {
		if (multipartFile.isEmpty() || multipartFile == null)
			throw new RuntimeException();
		final var manager = managerRepository.findById(userId).get();
		String keyForEmployeeProfilePicture = S3KeyUtility.getProfilePictureKey(manager);
		storageService.save(keyForEmployeeProfilePicture, multipartFile);
		manager.setImageUrl(keyForEmployeeProfilePicture);
		managerRepository.save(manager);
	}

	public ManagerDetailDto retreiveManager(UUID managerId) {
		final var manager = managerRepository.findById(managerId).get();
		String profilePicture = null;
		if (manager.getImageUrl() != null)
			try {
				profilePicture = Base64.encodeBase64String(
						storageService.getFile(manager.getImageUrl()).getObjectContent().readAllBytes());
			} catch (IOException e) {
				log.error("UNABLE TO RETREIVE S3 IMAGE WITH KEY " + manager.getImageUrl());
			}

		return ManagerDetailDto.builder().dateOfBirth(manager.getDateOfBirth()).description(manager.getDescription())
				.emailId(manager.getEmailId()).firstName(manager.getFirstName()).gender(manager.getGender())
				.profilePicture(profilePicture).lastName(manager.getLastName()).middleName(manager.getMiddleName())
				.status(manager.getStatus()).profileCompleted(manager.getGender() == null ? false : true).build();
	}

	public void acceptCompanyJoinRequest(AcceptCompanyJoinRequest acceptCompanyJoinRequest) {
		final var employee = employeeRepository.findById(acceptCompanyJoinRequest.getEmployeeId()).get();
		employee.setCompanyStatus(CompanyStatus.IN_COMPANY.getStatusId());
		final var employeeDailyAttendance = new EmployeeDailyAttendance();
		employeeDailyAttendance.setDate(LocalDate.now());
		final var savedEmployeeDailyAttendance = employeeDailyAttendanceRepository.save(employeeDailyAttendance);
		employee.setEmployeeDailyAttendanceId(savedEmployeeDailyAttendance.getId());
		final var savedEmployee = employeeRepository.save(employee);
		final var monthlySalaryDetail = new MonthlySalaryDetail();
		monthlySalaryDetail.setEmployeeId(savedEmployee.getId());
		monthlySalaryDetail.setSalary(acceptCompanyJoinRequest.getMonthlySalary());
		monthlySalaryDetailRepository.save(monthlySalaryDetail);
	}

	public void rejectCompanyJoinRequest(RejectCompanyJoinRequest rejectCompanyJoinRequest) {
		final var employee = employeeRepository.findById(rejectCompanyJoinRequest.getEmployeeId()).get();
		final var company = employee.getCompany();
		employee.setCompanyStatus(CompanyStatus.IN_NO_COMPANY.getStatusId());
		employee.setCompany(null);
		employee.setCompanyId(null);
		employeeRepository.save(employee);

		final var rejectedEmployeeMapping = rejectedEmployeeCompanyMappingRepository
				.findByEmployeeIdAndCompanyId(employee.getId(), company.getId())
				.orElse(new RejectedEmployeeCompanyMapping());
		rejectedEmployeeMapping.setEmployeeId(employee.getId());
		rejectedEmployeeMapping.setCompanyId(company.getId());
		rejectedEmployeeMapping.setIsActive(true);

		rejectedEmployeeCompanyMappingRepository.save(rejectedEmployeeMapping);
	}

	public void removeEmployeeFromCompany(RemoveEmployeeFromCompanyRequest removeEmployeeFromCompanyRequest) {
		final var employee = employeeRepository.findById(removeEmployeeFromCompanyRequest.getEmployeeId()).get();
		final var company = employee.getCompany();
		employee.setCompanyStatus(CompanyStatus.IN_NO_COMPANY.getStatusId());
		employee.setCompany(null);
		employee.setCompanyId(null);
		employeeDailyAttendanceRepository.deleteById(employee.getEmployeeDailyAttendanceId());
		monthlySalaryDetailRepository.delete(monthlySalaryDetailRepository.findByEmployeeId(employee.getId()).get());
		employee.setEmployeeDailyAttendanceId(null);
		employee.setEmployeeDailyAttendance(null);
		employeeRepository.save(employee);

		final var rejectedEmployeeMapping = rejectedEmployeeCompanyMappingRepository
				.findByEmployeeIdAndCompanyId(employee.getId(), company.getId())
				.orElse(new RejectedEmployeeCompanyMapping());
		rejectedEmployeeMapping.setEmployeeId(employee.getId());
		rejectedEmployeeMapping.setCompanyId(company.getId());
		rejectedEmployeeMapping.setIsActive(true);

		rejectedEmployeeCompanyMappingRepository.save(rejectedEmployeeMapping);
	}

}
