package com.hardik.flenderson.service;

import org.springframework.stereotype.Service;

import com.hardik.flenderson.repository.CompanyDocumentRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CompanyDocumentService {
	
	private final CompanyDocumentRepository companyDocumentRepository;

}
