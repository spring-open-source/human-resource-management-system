package com.hardik.flenderson.exception;

public class InvalidCompanyCodeAndNameException extends RuntimeException {

	private static final long serialVersionUID = -5440109489271058428L;

	public InvalidCompanyCodeAndNameException() {
		super();
	}

	public InvalidCompanyCodeAndNameException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidCompanyCodeAndNameException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidCompanyCodeAndNameException(String message) {
		super(message);
	}

	public InvalidCompanyCodeAndNameException(Throwable cause) {
		super(cause);
	}

}
