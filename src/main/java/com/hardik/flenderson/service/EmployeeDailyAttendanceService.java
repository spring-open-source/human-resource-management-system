package com.hardik.flenderson.service;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.hardik.flenderson.dto.DailyAttendanceStatusDto;
import com.hardik.flenderson.repository.EmployeeDailyAttendanceRepository;
import com.hardik.flenderson.repository.EmployeeRepository;
import com.hardik.flenderson.request.AttendanceMarkRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeDailyAttendanceService {

	private final EmployeeDailyAttendanceRepository employeeDailyAttendanceRepository;

	private final EmployeeRepository employeeRepository;

	public void markAttendance(AttendanceMarkRequest attendanceMarkRequest, UUID employeeId) {
		final var employee = employeeRepository.findById(employeeId).get();
		final var employeeDailyAttendance = employee.getEmployeeDailyAttendance();
		employeeDailyAttendance.setIsMarked(true);
		employeeDailyAttendance.setIsPresent(attendanceMarkRequest.getPresent());
		employeeDailyAttendance.setDate(LocalDate.now());
		employeeDailyAttendanceRepository.save(employeeDailyAttendance);
	}

	public DailyAttendanceStatusDto getDailyAttendanceStatus(UUID employeeId) {
		final var employee = employeeRepository.findById(employeeId).get();
		final var employeeDailyAttendance = employee.getEmployeeDailyAttendance();
		return DailyAttendanceStatusDto.builder().date(employeeDailyAttendance.getDate())
				.isMarked(employeeDailyAttendance.getIsMarked()).build();
	}

}
