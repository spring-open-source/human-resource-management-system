package com.hardik.flenderson.service;

import org.springframework.stereotype.Service;

import com.hardik.flenderson.repository.MasterRoleRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MasterRoleService {
	
	private final MasterRoleRepository masterRoleRepository;

}
