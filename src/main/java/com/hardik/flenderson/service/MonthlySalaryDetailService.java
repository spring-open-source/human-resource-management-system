package com.hardik.flenderson.service;

import org.springframework.stereotype.Service;

import com.hardik.flenderson.repository.MonthlySalaryDetailRepository;
import com.hardik.flenderson.request.EmployeeSalaryBonusRequest;
import com.hardik.flenderson.request.EmployeeSalaryPenaltyRequest;
import com.hardik.flenderson.request.MonthlySalaryDetailUpdationRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MonthlySalaryDetailService {

	private final MonthlySalaryDetailRepository monthlySalaryDetailRepository;

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

}
