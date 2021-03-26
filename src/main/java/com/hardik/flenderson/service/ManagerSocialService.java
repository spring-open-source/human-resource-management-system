package com.hardik.flenderson.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hardik.flenderson.entity.ManagerSocial;
import com.hardik.flenderson.enums.ExceptionMessage;
import com.hardik.flenderson.exception.InvalidManagerIdException;
import com.hardik.flenderson.exception.InvalidManagerSocialIdException;
import com.hardik.flenderson.exception.ManagerSocialWithNameAlreadyExistsException;
import com.hardik.flenderson.repository.ManagerRepository;
import com.hardik.flenderson.repository.ManagerSocialRepository;
import com.hardik.flenderson.request.ManagerSocialCreationRequest;
import com.hardik.flenderson.request.ManagerSocialUpdationRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ManagerSocialService {

	private final ManagerSocialRepository managerSocialRepository;

	private final ManagerRepository managerRepository;

	public void create(ManagerSocialCreationRequest managerSocialCreationRequest, UUID managerId) {
		final var manager = managerRepository.findById(managerId)
				.orElseThrow(() -> new InvalidManagerIdException(ExceptionMessage.INVALID_MANAGER_ID.getMessage()));
		if (managerSocialRepository.existsByManagerIdAndName(manager.getId(), managerSocialCreationRequest.getName()))
			throw new ManagerSocialWithNameAlreadyExistsException(
					ExceptionMessage.MANAGER_SOCIAL_WITH_NAME_ALREADY_EXISTS.getMessage().replace("--social-name--",
							managerSocialCreationRequest.getName()));
		final var managerSocial = new ManagerSocial();
		managerSocial.setName(managerSocialCreationRequest.getName());
		managerSocial.setUrl(managerSocialCreationRequest.getUrl());
		managerSocial.setManagerId(managerId);
		managerSocialRepository.save(managerSocial);

	}

	public void update(ManagerSocialUpdationRequest managerSocialUpdationRequest) {
		final var managerSocial = managerSocialRepository.findById(managerSocialUpdationRequest.getManagerSocialId())
				.orElseThrow(() -> new InvalidManagerSocialIdException(
						ExceptionMessage.INVALID_MANAGER_SOCIAL_ID.getMessage()));
		managerSocial.setUrl(managerSocialUpdationRequest.getUrl());
		managerSocialRepository.save(managerSocial);
	}

	public void delete(UUID managerSocialId) {
		final var managerSocial = managerSocialRepository.findById(managerSocialId).orElseThrow(
				() -> new InvalidManagerSocialIdException(ExceptionMessage.INVALID_MANAGER_SOCIAL_ID.getMessage()));
		managerSocialRepository.delete(managerSocial);
	}

	public List<ManagerSocial> retreive(UUID managerId) {
		return managerRepository.findById(managerId)
				.orElseThrow(() -> new InvalidManagerIdException(ExceptionMessage.INVALID_MANAGER_ID.getMessage()))
				.getManagerSocials().parallelStream().collect(Collectors.toList());
	}

}
