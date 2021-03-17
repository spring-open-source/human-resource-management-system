package com.hardik.flenderson.service;

import org.springframework.stereotype.Service;

import com.hardik.flenderson.repository.MonthlySalaryDetailRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MonthlySalaryDetailService {
	
	private final MonthlySalaryDetailRepository monthlySalaryDetailRepository;

}
