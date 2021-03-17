package com.hardik.flenderson.service;

import org.springframework.stereotype.Service;

import com.hardik.flenderson.repository.AttendanceRecordRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AttendanceRecordService {
	
	private final AttendanceRecordRepository attendanceRecordRepository;

}
