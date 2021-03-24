package com.hardik.flenderson.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class DailyAttendanceDto {

	private final UUID companyId;
	private final UUID employeeId;
	private final boolean present;
	private final boolean marked;
	@JsonSerialize(using = ToStringSerializer.class) 
	private final LocalDate date;

}