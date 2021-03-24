package com.hardik.flenderson.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hardik.flenderson.entity.MasterRole;
import com.hardik.flenderson.repository.MasterRoleRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MasterRoleService {
	
	private final MasterRoleRepository masterRoleRepository;

	public List<MasterRole> getMasterRoles() {
		return masterRoleRepository.findAll().subList(0, 20);
	}

}
