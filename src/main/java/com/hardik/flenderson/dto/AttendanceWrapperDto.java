package com.hardik.flenderson.dto;
import java.util.ArrayList;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class AttendanceWrapperDto {
	
	private ArrayList<DailyAttendanceDto> data;

}