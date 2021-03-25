package com.hardik.flenderson.exception.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.hardik.flenderson.exception.InvalidEmployeeRoleIdException;
import com.hardik.flenderson.exception.dto.ExceptionResponseDto;

@RestControllerAdvice
public class InvalidEmployeeRoleIdExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(InvalidEmployeeRoleIdException.class)
	public ResponseEntity<ExceptionResponseDto> handler(Exception exception, WebRequest webRequest) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(ExceptionResponseDto.builder().timestamp(LocalDateTime.now())
						.statusCode(HttpStatus.BAD_REQUEST.value()).message(exception.getMessage())
						.exception(exception.getClass().getSimpleName().toUpperCase()).build());
	}

}
