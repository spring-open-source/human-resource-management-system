package com.hardik.flenderson.exception.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class ExceptionResponseDto {
	
	private final Integer statusCode;
	private final LocalDateTime timestamp;
	private final String exception;
	private final String message;
	
}
