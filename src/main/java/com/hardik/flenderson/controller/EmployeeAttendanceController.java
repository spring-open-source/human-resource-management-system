package com.hardik.flenderson.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hardik.flenderson.dto.DailyAttendanceStatusDto;
import com.hardik.flenderson.interceptor.AuthenticationInterceptor;
import com.hardik.flenderson.request.AttendanceMarkRequest;
import com.hardik.flenderson.service.EmployeeDailyAttendanceService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class EmployeeAttendanceController extends AuthenticationInterceptor {

	private final EmployeeDailyAttendanceService employeeDailyAttendanceService;

	@PostMapping("v1/daily-attendance")
	public void dailyAttendanceMarkerHandler(
			@RequestBody(required = true) final AttendanceMarkRequest attendanceMarkRequest) {
		employeeDailyAttendanceService.markAttendance(attendanceMarkRequest, getUserDetails().getUserId());
	}

	@GetMapping("v1/daily-attendance")
	public DailyAttendanceStatusDto getDailyAttendanceStatusHandler() {
		return employeeDailyAttendanceService.getDailyAttendanceStatus(getUserDetails().getUserId());
	}

}
