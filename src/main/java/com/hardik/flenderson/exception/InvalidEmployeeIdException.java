package com.hardik.flenderson.exception;

public class InvalidEmployeeIdException extends RuntimeException{

	private static final long serialVersionUID = 586703659870455182L;

	public InvalidEmployeeIdException() {
		super();
	}

	public InvalidEmployeeIdException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidEmployeeIdException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidEmployeeIdException(String message) {
		super(message);
	}

	public InvalidEmployeeIdException(Throwable cause) {
		super(cause);
	}
	
}
