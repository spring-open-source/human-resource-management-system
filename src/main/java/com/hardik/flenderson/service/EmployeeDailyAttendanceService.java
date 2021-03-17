package com.hardik.flenderson.service;

import org.springframework.stereotype.Service;

import com.hardik.flenderson.repository.EmployeeDailyAttendanceRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeDailyAttendanceService {
	
	private final EmployeeDailyAttendanceRepository employeeDailyAttendanceRepository;

}
