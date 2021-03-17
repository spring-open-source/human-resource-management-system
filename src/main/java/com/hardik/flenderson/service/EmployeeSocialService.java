package com.hardik.flenderson.service;

import org.springframework.stereotype.Service;

import com.hardik.flenderson.repository.EmployeeSocialRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeSocialService {
	
	private final EmployeeSocialRepository employeeSocialRepository;

}
