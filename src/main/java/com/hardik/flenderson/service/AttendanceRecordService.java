package com.hardik.flenderson.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Month;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.dhatim.fastexcel.Color;
import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.hardik.flenderson.dto.AttendanceWrapperDto;
import com.hardik.flenderson.entity.AttendanceRecord;
import com.hardik.flenderson.enums.ExceptionMessage;
import com.hardik.flenderson.exception.InvalidCompanyIdException;
import com.hardik.flenderson.exception.InvalidEmployeeEmailIdException;
import com.hardik.flenderson.mailing.dto.MonthlyAttendanceReportDto;
import com.hardik.flenderson.mailing.event.MonthlyAttendanceReportEvent;
import com.hardik.flenderson.repository.AttendanceRecordRepository;
import com.hardik.flenderson.repository.CompanyRepository;
import com.hardik.flenderson.repository.EmployeeRepository;
import com.hardik.flenderson.storage.StorageService;
import com.hardik.flenderson.utility.CountUtility;
import com.hardik.flenderson.utility.S3KeyUtility;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AttendanceRecordService {

	private final AttendanceRecordRepository attendanceRecordRepository;

	private final CompanyRepository companyRepository;

	private final EmployeeRepository employeeRepository;

	private final StorageService storageService;

	private final ApplicationEventPublisher applicationEventPublisher;

	public void saveAll(AttendanceWrapperDto attendanceWrapperDto) {
		final var attendanceRecords = new ArrayList<AttendanceRecord>();
		attendanceWrapperDto.getData().forEach(dailyAttendanceDto -> {
			final var attendanceRecord = new AttendanceRecord();
			attendanceRecord.setCompanyId(dailyAttendanceDto.getCompanyId());
			attendanceRecord.setEmployeeId(dailyAttendanceDto.getEmployeeId());
			attendanceRecord.setMarked(dailyAttendanceDto.isMarked());
			attendanceRecord.setPresent(dailyAttendanceDto.isPresent());

			final var dateToRecord = dailyAttendanceDto.getDate();
			attendanceRecord.setDay(dateToRecord.getDayOfMonth());
			attendanceRecord.setMonth(dateToRecord.getMonthValue());
			attendanceRecord.setYear(dateToRecord.getYear());

			attendanceRecords.add(attendanceRecord);
		});
		attendanceRecordRepository.saveAll(attendanceRecords);
	}

	public void generateExcelFiles(Integer month, Integer year) {
		final var attendanceRecordByCompanyIds = attendanceRecordRepository.findByMonthAndYear(month, year).stream()
				.collect(Collectors.groupingBy(AttendanceRecord::getCompanyId));

		attendanceRecordByCompanyIds.keySet().forEach(attendanceRecordByCompanyId -> {
			final var company = companyRepository.findById(attendanceRecordByCompanyId).orElseThrow(
					() -> new InvalidCompanyIdException(ExceptionMessage.INVALID_COMPANY_ID_EXCEPTION.getMessage()));
			final var manager = company.getManagers().stream().findFirst().get();
			try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
				Workbook workBook = new Workbook(outputStream, "Attendance Record For Month " + month + ", " + year,
						"1.0");
				Worksheet workSheet = workBook.newWorksheet("attendance-record-" + month + "-" + year);
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
				workSheet.value(1, 3, "TOTAL-DAYS");
				workSheet.style(1, 3).bold().fillColor(Color.LIGHT_TURQUOISE).fontSize(10).set();
				workSheet.value(1, 4, "TOTAL-DAYs-PRESENT");
				workSheet.style(1, 4).bold().fillColor(Color.LIGHT_TURQUOISE).fontSize(10).set();
				workSheet.value(1, 5, "TOTAL-DAYS-ABSENT");
				workSheet.style(1, 5).bold().fillColor(Color.LIGHT_TURQUOISE).fontSize(10).set();
				workSheet.value(1, 6, "TOTAL-DAYS-UNMARKED");
				workSheet.style(1, 6).bold().fillColor(Color.LIGHT_TURQUOISE).fontSize(10).set();
				workSheet.value(1, 7, "MONTH");
				workSheet.style(1, 7).bold().fillColor(Color.LIGHT_TURQUOISE).fontSize(10).set();
				workSheet.value(1, 8, "YEAR");
				workSheet.style(1, 8).bold().fillColor(Color.LIGHT_TURQUOISE).fontSize(10).set();

				final var employeeAttendanceRecordForMonthForCompany = attendanceRecordByCompanyIds
						.get(attendanceRecordByCompanyId).stream()
						.collect(Collectors.groupingBy(AttendanceRecord::getEmployeeId));

				var count = CountUtility.createCount();
				var workSheetRowValue = count.getCount();

				employeeAttendanceRecordForMonthForCompany.keySet().forEach(employeeId -> {
					final var employee = employeeRepository.findById(employeeId)
							.orElseThrow(() -> new InvalidEmployeeEmailIdException(
									ExceptionMessage.INVALID_EMPLOYEE_ID.getMessage()));
					final var numberOfDaysPresent = employeeAttendanceRecordForMonthForCompany.get(employeeId).stream()
							.filter(employeeAttendanceRecord -> employeeAttendanceRecord.getPresent().equals(true))
							.collect(Collectors.toList()).size();
					final var numberOfDaysUnmarked = employeeAttendanceRecordForMonthForCompany.get(employeeId).stream()
							.filter(employeeAttendanceRecord -> employeeAttendanceRecord.getMarked().equals(false))
							.collect(Collectors.toList()).size();

					workSheet.value(workSheetRowValue, 0, employee.getEmailId());
					workSheet.value(workSheetRowValue, 1,
							employee.getFirstName() + " " + employee.getMiddleName() + " " + employee.getLastName());
					workSheet.value(workSheetRowValue, 2, company.getName());
					int maxMonthLength = Month.of(month).maxLength();
					workSheet.value(workSheetRowValue, 3, maxMonthLength);
					workSheet.value(workSheetRowValue, 4, numberOfDaysPresent);
					workSheet.value(workSheetRowValue, 5, maxMonthLength - numberOfDaysPresent);
					workSheet.value(workSheetRowValue, 6, numberOfDaysUnmarked);
					workSheet.value(workSheetRowValue, 7, Month.of(month).name());
					workSheet.value(workSheetRowValue, 8, year);
					count.increment();
				});
				count.refresh();
				workBook.finish();
				outputStream.flush();
				storageService.save(S3KeyUtility.getMonthlyAttendanceRecordKey(month, year, manager, company),
						new ByteArrayInputStream(outputStream.toByteArray()));
				applicationEventPublisher.publishEvent(new MonthlyAttendanceReportEvent(
						MonthlyAttendanceReportDto.builder().companyName(company.getName()).email(manager.getEmailId())
								.managerName(manager.getFirstName() + " " + manager.getLastName())
								.month(Month.of(month).name()).build()));
			} catch (IOException e) {
				e.printStackTrace();
			}

		});
	}

}
