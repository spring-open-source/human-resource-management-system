package com.hardik.flenderson.service;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.hardik.flenderson.dto.MonthlySalaryWrapperDto;
import com.hardik.flenderson.entity.SalaryRecord;
import com.hardik.flenderson.repository.SalaryRecordRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SalaryRecordService {

	private final SalaryRecordRepository salaryRecordRepository;

	public void saveAll(MonthlySalaryWrapperDto monthlySalaryWrapperDto) {
		final var salaryRecords = new ArrayList<SalaryRecord>();
		monthlySalaryWrapperDto.getData().forEach(monthlySalaryRecordDto -> {
			final var salaryRecord = new SalaryRecord();
			salaryRecord.setBonusForMonth(monthlySalaryRecordDto.getBonusForMonth());
			salaryRecord.setCompanyId(monthlySalaryRecordDto.getCompanyId());
			salaryRecord.setEmployeeId(monthlySalaryRecordDto.getEmployeeId());
			salaryRecord.setPenaltyForMonth(monthlySalaryRecordDto.getPenaltyForMonth());
			salaryRecord.setPaid(true); // WILL BE DYNAMIC IF I INTEGRATE PAYMENT SYSTEM
			salaryRecord.setTotalAmountPayable(
					(monthlySalaryRecordDto.getMonthlySalary() - monthlySalaryRecordDto.getPenaltyForMonth())
							+ monthlySalaryRecordDto.getBonusForMonth());
			salaryRecord.setMonth(LocalDate.now().minusDays(1).getMonthValue());
			salaryRecord.setYear(LocalDate.now().minusDays(1).getYear());
			salaryRecords.add(salaryRecord);

		});
		salaryRecordRepository.saveAll(salaryRecords);
	}

}
