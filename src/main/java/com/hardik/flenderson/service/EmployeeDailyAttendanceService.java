package com.hardik.flenderson.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hardik.flenderson.dto.AttendanceWrapperDto;
import com.hardik.flenderson.dto.DailyAttendanceDto;
import com.hardik.flenderson.dto.DailyAttendanceStatusDto;
import com.hardik.flenderson.enums.CompanyStatus;
import com.hardik.flenderson.enums.SnsTopic;
import com.hardik.flenderson.notification.NotificationService;
import com.hardik.flenderson.repository.EmployeeDailyAttendanceRepository;
import com.hardik.flenderson.repository.EmployeeRepository;
import com.hardik.flenderson.request.AttendanceMarkRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeDailyAttendanceService {

	private final EmployeeDailyAttendanceRepository employeeDailyAttendanceRepository;

	private final EmployeeRepository employeeRepository;

	private final NotificationService notificationService;

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

	public void publishDailyRecords() {
		final ArrayList<DailyAttendanceDto> dailyAttendanceDtos = new ArrayList<DailyAttendanceDto>();
		final var employeesInCompany = employeeRepository.findByCompanyStatus(CompanyStatus.IN_COMPANY.getStatusId());
		employeesInCompany.forEach(employee -> {
			final var dailyAttendance = employee.getEmployeeDailyAttendance();
			dailyAttendanceDtos.add(DailyAttendanceDto.builder().companyId(employee.getCompanyId())
					.date(LocalDate.now().minusDays(1)).employeeId(employee.getId())
					.marked(dailyAttendance.getIsMarked()).present(dailyAttendance.getIsPresent()).build());
		});
		try {
			notificationService.publishMessage(
					new ObjectMapper()
							.writeValueAsString(AttendanceWrapperDto.builder().data(dailyAttendanceDtos).build()),
					SnsTopic.ATTENDANCE_TOPIC.getTopicName());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	public void refreshDailyAttendance() {
		final var employeesInCompany = employeeRepository.findByCompanyStatus(CompanyStatus.IN_COMPANY.getStatusId());
		employeesInCompany.forEach(employee -> {
			final var dailyAttendance = employee.getEmployeeDailyAttendance();
			dailyAttendance.setDate(LocalDate.now());
			dailyAttendance.setIsMarked(false);
			dailyAttendance.setIsPresent(false);
			employeeDailyAttendanceRepository.save(dailyAttendance);
		});
	}

}
