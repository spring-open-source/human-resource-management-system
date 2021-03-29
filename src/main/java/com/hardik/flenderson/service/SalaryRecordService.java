package com.hardik.flenderson.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.dhatim.fastexcel.Color;
import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.hardik.flenderson.dto.MonthlySalaryWrapperDto;
import com.hardik.flenderson.entity.AttendanceRecord;
import com.hardik.flenderson.entity.CompanyReport;
import com.hardik.flenderson.entity.SalaryRecord;
import com.hardik.flenderson.enums.ExceptionMessage;
import com.hardik.flenderson.enums.ReportType;
import com.hardik.flenderson.exception.InvalidCompanyIdException;
import com.hardik.flenderson.exception.InvalidEmployeeIdException;
import com.hardik.flenderson.mailing.dto.MonthlyPayrollReportDto;
import com.hardik.flenderson.mailing.event.MonthlyPayrollReportEvent;
import com.hardik.flenderson.repository.CompanyReportRepository;
import com.hardik.flenderson.repository.CompanyRepository;
import com.hardik.flenderson.repository.EmployeeRepository;
import com.hardik.flenderson.repository.SalaryRecordRepository;
import com.hardik.flenderson.storage.StorageService;
import com.hardik.flenderson.utility.CountUtility;
import com.hardik.flenderson.utility.S3KeyUtility;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SalaryRecordService {

	private final SalaryRecordRepository salaryRecordRepository;

	private final CompanyRepository companyRepository;

	private final EmployeeRepository employeeRepository;

	private final StorageService storageService;

	private final ApplicationEventPublisher applicationEventPublisher;
	
	private final CompanyReportRepository companyReportRepository;

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

	public void generateExcelFiles(int month, int year) {
		final var payrollRecordByCompanyIds = salaryRecordRepository.findByMonthAndYear(month, year).stream()
				.collect(Collectors.groupingBy(SalaryRecord::getCompanyId));

		payrollRecordByCompanyIds.keySet().forEach(salaryRecordByCompanyId -> {
			final var company = companyRepository.findById(salaryRecordByCompanyId).orElseThrow(
					() -> new InvalidCompanyIdException(ExceptionMessage.INVALID_COMPANY_ID_EXCEPTION.getMessage()));
			final var manager = company.getManagers().stream().findFirst().get();
			try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
				Workbook workBook = new Workbook(outputStream,
						"Payroll Record For Month " + Month.of(month).name() + ", " + year, "1.0");
				Worksheet workSheet = workBook.newWorksheet("payroll-record-" + Month.of(month).name() + "-" + year);
				workSheet.value(0, 0, company.getName().toUpperCase() + " ATTENDANCE RECORD FOR " + month + "-" + year);
				workSheet.style(0, 0).bold().fillColor(Color.BLACK).fontColor(Color.WHITE).horizontalAlignment("center")
						.set();
				workSheet.range(0, 0, 0, 8).style().merge();

				workSheet.value(1, 0, "EMPLOYEE-EMAIL");
				workSheet.style(1, 0).bold().fillColor(Color.LIGHT_TURQUOISE).fontSize(10).set();
				workSheet.value(1, 1, "EMPLOYEE-NAME");
				workSheet.style(1, 1).bold().fillColor(Color.LIGHT_TURQUOISE).fontSize(10).set();
				workSheet.value(1, 2, "COMPANY-NAME");
				workSheet.style(1, 2).bold().fillColor(Color.LIGHT_TURQUOISE).fontSize(10).set();
				workSheet.value(1, 3, "BONUS-FOR_MONTH");
				workSheet.style(1, 3).bold().fillColor(Color.LIGHT_TURQUOISE).fontSize(10).set();
				workSheet.value(1, 4, "PENALTY-FOR-MONTH");
				workSheet.style(1, 4).bold().fillColor(Color.LIGHT_TURQUOISE).fontSize(10).set();
				workSheet.value(1, 5, "TOTAL-AMOUNT-PAYABLE");
				workSheet.style(1, 5).bold().fillColor(Color.LIGHT_TURQUOISE).fontSize(10).set();
				workSheet.value(1, 6, "PAID");
				workSheet.style(1, 6).bold().fillColor(Color.LIGHT_TURQUOISE).fontSize(10).set();
				workSheet.value(1, 7, "MONTH");
				workSheet.style(1, 7).bold().fillColor(Color.LIGHT_TURQUOISE).fontSize(10).set();
				workSheet.value(1, 8, "YEAR");
				workSheet.style(1, 8).bold().fillColor(Color.LIGHT_TURQUOISE).fontSize(10).set();

				final var employeeSalaryRecordForMonthForCompany = payrollRecordByCompanyIds
						.get(salaryRecordByCompanyId).stream()
						.collect(Collectors.groupingBy(SalaryRecord::getEmployeeId));

				var count = CountUtility.createCount();
				var workSheetRowValue = count.getCount();

				employeeSalaryRecordForMonthForCompany.keySet().forEach(employeeId -> {
					final var employee = employeeRepository.findById(employeeId).orElseThrow(
							() -> new InvalidEmployeeIdException(ExceptionMessage.INVALID_EMPLOYEE_ID.getMessage()));
					final var salaryRecord = employeeSalaryRecordForMonthForCompany.get(employeeId).get(0);

					workSheet.value(workSheetRowValue, 0, employee.getEmailId());
					workSheet.value(workSheetRowValue, 1,
							employee.getFirstName() + " " + employee.getMiddleName() + " " + employee.getLastName());
					workSheet.value(workSheetRowValue, 2, company.getName());
					workSheet.value(workSheetRowValue, 3, salaryRecord.getBonusForMonth());
					workSheet.value(workSheetRowValue, 4, salaryRecord.getPenaltyForMonth());
					workSheet.value(workSheetRowValue, 5, salaryRecord.getTotalAmountPayable());
					workSheet.value(workSheetRowValue, 6, salaryRecord.getPaid() ? "TRUE" : "FALSE");
					workSheet.value(workSheetRowValue, 7, Month.of(month).name());
					workSheet.value(workSheetRowValue, 8, year);
					count.increment();
				});
				count.refresh();
				workBook.finish();
				outputStream.flush();
				String monthlyPayrollRecordKey = S3KeyUtility.getMonthlyPayrollRecordKey(month, year, manager, company);
				storageService.save(monthlyPayrollRecordKey,
						new ByteArrayInputStream(outputStream.toByteArray()));
				applicationEventPublisher.publishEvent(new MonthlyPayrollReportEvent(
						MonthlyPayrollReportDto.builder().companyName(company.getName()).email(manager.getEmailId())
								.managerName(manager.getFirstName() + " " + manager.getLastName())
								.month(Month.of(month).name()).build()));
				
				final var companyReport = new CompanyReport();
				companyReport.setCompanyId(company.getId());
				companyReport.setMonth(month);
				companyReport.setYear(year);
				companyReport.setReportType(ReportType.MONTHLY_ATTENDANCE_RECORD.getId());
				companyReport.setS3Key(monthlyPayrollRecordKey);
				companyReportRepository.save(companyReport);

			} catch (IOException e) {
				e.printStackTrace();
			}
		});

	}

}
