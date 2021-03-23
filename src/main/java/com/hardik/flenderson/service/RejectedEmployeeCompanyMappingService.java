package com.hardik.flenderson.service;

import org.springframework.stereotype.Service;

import com.hardik.flenderson.entity.RejectedEmployeeCompanyMapping;
import com.hardik.flenderson.repository.RejectedEmployeeCompanyMappingRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RejectedEmployeeCompanyMappingService {
	
	private final RejectedEmployeeCompanyMappingRepository rejectedEmployeeCompanyMappingRepository;

}
