package com.hardik.flenderson.service;

import org.springframework.stereotype.Service;

import com.hardik.flenderson.entity.Manager;
import com.hardik.flenderson.keycloak.dto.KeycloakUserDto;
import com.hardik.flenderson.repository.ManagerRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ManagerService {
	
	private final ManagerRepository managerRepository;

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

}
