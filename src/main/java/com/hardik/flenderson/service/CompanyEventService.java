package com.hardik.flenderson.service;

import org.springframework.stereotype.Service;

import com.hardik.flenderson.repository.CompanyEventRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CompanyEventService {
	
	private final CompanyEventRepository companyEventRepository;

}
