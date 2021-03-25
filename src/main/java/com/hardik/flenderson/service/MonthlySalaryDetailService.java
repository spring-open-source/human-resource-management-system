package com.hardik.flenderson.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hardik.flenderson.dto.MonthlySalaryRecordDto;
import com.hardik.flenderson.dto.MonthlySalaryWrapperDto;
import com.hardik.flenderson.enums.ExceptionMessage;
import com.hardik.flenderson.enums.SnsTopic;
import com.hardik.flenderson.exception.InvalidEmployeeIdException;
import com.hardik.flenderson.notification.NotificationService;
import com.hardik.flenderson.repository.EmployeeRepository;
import com.hardik.flenderson.repository.MonthlySalaryDetailRepository;
import com.hardik.flenderson.request.EmployeeSalaryBonusRequest;
import com.hardik.flenderson.request.EmployeeSalaryPenaltyRequest;
import com.hardik.flenderson.request.MonthlySalaryDetailUpdationRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MonthlySalaryDetailService {

	private final MonthlySalaryDetailRepository monthlySalaryDetailRepository;

	private final NotificationService notificationService;

	private final EmployeeRepository employeeRepository;

	public void update(MonthlySalaryDetailUpdationRequest monthlySalaryDetailUpdationRequest) {
		final var monthlySalaryDetails = monthlySalaryDetailRepository
				.findByEmployeeId(monthlySalaryDetailUpdationRequest.getEmployeeId()).get();
		monthlySalaryDetails.setSalary(monthlySalaryDetailUpdationRequest.getMonthlySalary());
		monthlySalaryDetailRepository.save(monthlySalaryDetails);
	}

	public void addMonthlyBonus(EmployeeSalaryBonusRequest employeeSalaryBonusRequest) {
		final var monthlySalaryDetails = monthlySalaryDetailRepository
				.findByEmployeeId(employeeSalaryBonusRequest.getEmployeeId()).get();
		monthlySalaryDetails.setBonus(monthlySalaryDetails.getBonus() + employeeSalaryBonusRequest.getBonus());
		monthlySalaryDetailRepository.save(monthlySalaryDetails);
	}

	public void deductMonthlyPenalty(EmployeeSalaryPenaltyRequest employeeSalaryPenaltyRequest) {
		final var monthlySalaryDetails = monthlySalaryDetailRepository
				.findByEmployeeId(employeeSalaryPenaltyRequest.getEmployeeId()).get();
		monthlySalaryDetails.setPenalty(monthlySalaryDetails.getPenalty() + employeeSalaryPenaltyRequest.getPenalty());
		monthlySalaryDetailRepository.save(monthlySalaryDetails);
	}

	public void publishMonthlyRecords() {
		final ArrayList<MonthlySalaryRecordDto> monthlySalaryRecords = new ArrayList<MonthlySalaryRecordDto>();
		final var monthlySalaryDetails = monthlySalaryDetailRepository.findAll();
		monthlySalaryDetails.forEach(monthlySalaryDetail -> {
			final var employee = employeeRepository.findById(monthlySalaryDetail.getEmployeeId()).orElseThrow(
					() -> new InvalidEmployeeIdException(ExceptionMessage.INVALID_EMPLOYEE_ID.getMessage()));
			monthlySalaryRecords.add(MonthlySalaryRecordDto.builder().bonusForMonth(monthlySalaryDetail.getBonus())
					.companyId(employee.getCompanyId()).employeeId(employee.getId())
					.monthlySalary(monthlySalaryDetail.getSalary()).penaltyForMonth(monthlySalaryDetail.getPenalty())
					.build());
		});
		try {
			notificationService.publishMessage(
					new ObjectMapper()
							.writeValueAsString(MonthlySalaryWrapperDto.builder().data(monthlySalaryRecords).build()),
					SnsTopic.PAYROLL_TOPIC.getTopicName());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	public void refreshMonthlySalaryDetails() {
		final var monthlySalaryDetails = monthlySalaryDetailRepository.findAll();
		monthlySalaryDetails.forEach(monthlySalaryDetail -> {
			monthlySalaryDetail.setBonus(0.0);
			monthlySalaryDetail.setPenalty(0.0);
			monthlySalaryDetailRepository.save(monthlySalaryDetail);
		});
	}

}
