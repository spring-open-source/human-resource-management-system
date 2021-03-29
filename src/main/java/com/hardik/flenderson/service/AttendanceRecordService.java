package com.hardik.flenderson.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.hardik.flenderson.dto.AttendanceWrapperDto;
import com.hardik.flenderson.entity.AttendanceRecord;
import com.hardik.flenderson.repository.AttendanceRecordRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AttendanceRecordService {
	
	private final AttendanceRecordRepository attendanceRecordRepository;

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

}
