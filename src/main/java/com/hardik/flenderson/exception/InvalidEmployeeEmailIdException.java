package com.hardik.flenderson.exception;

public class InvalidEmployeeEmailIdException extends RuntimeException{

	private static final long serialVersionUID = 6239851947996096469L;

	public InvalidEmployeeEmailIdException() {
		super();
	}

	public InvalidEmployeeEmailIdException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidEmployeeEmailIdException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidEmployeeEmailIdException(String message) {
		super(message);
	}

	public InvalidEmployeeEmailIdException(Throwable cause) {
		super(cause);
	}

}
