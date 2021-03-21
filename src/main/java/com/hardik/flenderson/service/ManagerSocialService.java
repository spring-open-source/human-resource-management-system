package com.hardik.flenderson.service;

import org.springframework.stereotype.Service;

import com.hardik.flenderson.repository.ManagerSocialRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ManagerSocialService {
	
	private final ManagerSocialRepository managerSocialRepository;

}
