package com.hardik.flenderson.service;

import java.io.IOException;
import java.util.UUID;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hardik.flenderson.dto.ManagerDetailDto;
import com.hardik.flenderson.entity.Manager;
import com.hardik.flenderson.keycloak.dto.KeycloakUserDto;
import com.hardik.flenderson.repository.ManagerRepository;
import com.hardik.flenderson.request.ManagerDetailUpdationRequest;
import com.hardik.flenderson.storage.StorageService;
import com.hardik.flenderson.utility.S3KeyUtility;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class ManagerService {

	private final ManagerRepository managerRepository;

	private final StorageService storageService;

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

}
