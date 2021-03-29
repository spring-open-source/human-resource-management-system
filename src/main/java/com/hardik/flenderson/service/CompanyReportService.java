package com.hardik.flenderson.service;

import org.springframework.stereotype.Service;

import com.hardik.flenderson.repository.CompanyReportRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CompanyReportService {
	
	private final CompanyReportRepository companyReportRepository;

}
