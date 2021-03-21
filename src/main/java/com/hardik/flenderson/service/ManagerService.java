package com.hardik.flenderson.service;

import org.springframework.stereotype.Service;

import com.hardik.flenderson.repository.ManagerRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ManagerService {
	
	private final ManagerRepository managerRepository;

}
