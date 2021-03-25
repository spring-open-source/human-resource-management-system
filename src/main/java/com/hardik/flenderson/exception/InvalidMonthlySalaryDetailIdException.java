package com.hardik.flenderson.exception;

public class InvalidMonthlySalaryDetailIdException extends RuntimeException{

	private static final long serialVersionUID = 1048983335542229152L;

	public InvalidMonthlySalaryDetailIdException() {
		super();
	}

	public InvalidMonthlySalaryDetailIdException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidMonthlySalaryDetailIdException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidMonthlySalaryDetailIdException(String message) {
		super(message);
	}

	public InvalidMonthlySalaryDetailIdException(Throwable cause) {
		super(cause);
	}

}
