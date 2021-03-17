package com.hardik.flenderson.service;

import org.springframework.stereotype.Service;

import com.hardik.flenderson.repository.SalaryRecordRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SalaryRecordService {
	
	private final SalaryRecordRepository salaryRecordRepository;

}
