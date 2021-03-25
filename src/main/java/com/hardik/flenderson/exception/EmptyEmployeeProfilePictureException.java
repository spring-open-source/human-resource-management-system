package com.hardik.flenderson.exception;

public class EmptyEmployeeProfilePictureException extends RuntimeException {

	private static final long serialVersionUID = 5279152922961940616L;

	public EmptyEmployeeProfilePictureException() {
		super();
	}

	public EmptyEmployeeProfilePictureException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EmptyEmployeeProfilePictureException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmptyEmployeeProfilePictureException(String message) {
		super(message);
	}

	public EmptyEmployeeProfilePictureException(Throwable cause) {
		super(cause);
	}

}
