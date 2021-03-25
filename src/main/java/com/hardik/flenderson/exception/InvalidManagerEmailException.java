package com.hardik.flenderson.exception;

public class InvalidManagerEmailException extends RuntimeException{

	private static final long serialVersionUID = 4667962796969904825L;

	public InvalidManagerEmailException() {
		super();
	}

	public InvalidManagerEmailException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidManagerEmailException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidManagerEmailException(String message) {
		super(message);
	}

	public InvalidManagerEmailException(Throwable cause) {
		super(cause);
	}

}
