package com.hardik.flenderson.dto;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import lombok.Getter;

@Getter
@Builder
@Jacksonized
public class DailyAttendanceStatusDto {
	
	private final boolean isMarked;
	private final LocalDate date;
	
}
