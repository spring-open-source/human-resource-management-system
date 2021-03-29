package com.hardik.flenderson.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hardik.flenderson.entity.CompanyReport;
import com.hardik.flenderson.enums.ExceptionMessage;
import com.hardik.flenderson.exception.InvalidManagerIdException;
import com.hardik.flenderson.repository.CompanyReportRepository;
import com.hardik.flenderson.repository.ManagerRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CompanyReportService {

	private final CompanyReportRepository companyReportRepository;

	private final ManagerRepository managerRepository;

	public List<CompanyReport> retreive(Integer reportType, UUID managerId) {
		final var manager = managerRepository.findById(managerId)
				.orElseThrow(() -> new InvalidManagerIdException(ExceptionMessage.INVALID_MANAGER_ID.getMessage()));
		final var company = manager.getCompany();
		return companyReportRepository.findByCompanyId(company.getId()).stream()
				.filter(companyReport -> companyReport.getReportType().equals(reportType)).collect(Collectors.toList());
	}

}
