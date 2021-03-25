package com.hardik.flenderson.exception;

public class EmployeeSocialWithNameAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 2437960160113199922L;

	public EmployeeSocialWithNameAlreadyExistsException() {
		super();
	}

	public EmployeeSocialWithNameAlreadyExistsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EmployeeSocialWithNameAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmployeeSocialWithNameAlreadyExistsException(String message) {
		super(message);
	}

	public EmployeeSocialWithNameAlreadyExistsException(Throwable cause) {
		super(cause);
	}

}
