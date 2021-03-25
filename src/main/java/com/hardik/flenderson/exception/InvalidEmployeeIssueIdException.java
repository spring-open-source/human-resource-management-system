package com.hardik.flenderson.exception;

public class InvalidEmployeeIssueIdException extends RuntimeException{

	private static final long serialVersionUID = -5495781544620068206L;

	public InvalidEmployeeIssueIdException() {
		super();
	}

	public InvalidEmployeeIssueIdException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidEmployeeIssueIdException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidEmployeeIssueIdException(String message) {
		super(message);
	}

	public InvalidEmployeeIssueIdException(Throwable cause) {
		super(cause);
	}

}
