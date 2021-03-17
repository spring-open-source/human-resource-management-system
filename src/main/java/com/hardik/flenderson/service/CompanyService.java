package com.hardik.flenderson.service;

import org.springframework.stereotype.Service;

import com.hardik.flenderson.repository.CompanyRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CompanyService {
	
	private final CompanyRepository companyRepository;

}
