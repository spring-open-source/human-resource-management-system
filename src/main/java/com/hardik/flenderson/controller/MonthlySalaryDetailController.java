package com.hardik.flenderson.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hardik.flenderson.interceptor.AuthenticationInterceptor;
import com.hardik.flenderson.request.EmployeeSalaryBonusRequest;
import com.hardik.flenderson.request.EmployeeSalaryPenaltyRequest;
import com.hardik.flenderson.request.MonthlySalaryDetailUpdationRequest;
import com.hardik.flenderson.service.MonthlySalaryDetailService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class MonthlySalaryDetailController extends AuthenticationInterceptor {

	private final MonthlySalaryDetailService monthlySalaryDetailService;

	@PutMapping("v1/salary-details")
	public void updateEmployeeSalaryDetailHandler(
			@RequestBody(required = true) final MonthlySalaryDetailUpdationRequest monthlySalaryDetailUpdationRequest) {
		monthlySalaryDetailService.update(monthlySalaryDetailUpdationRequest);
	}

	@PostMapping("v1/salary-bonus")
	public void employeeMonthlySalaryBonusHandler(
			@RequestBody(required = true) final EmployeeSalaryBonusRequest employeeSalaryBonusRequest) {
		monthlySalaryDetailService.addMonthlyBonus(employeeSalaryBonusRequest);
	}

	@PostMapping("v1/salary-penalty")
	public void employeeMonthlySalaryPenaltyHandler(
			@RequestBody(required = true) final EmployeeSalaryPenaltyRequest employeeSalaryPenaltyRequest) {
		monthlySalaryDetailService.deductMonthlyPenalty(employeeSalaryPenaltyRequest);
	}
}
