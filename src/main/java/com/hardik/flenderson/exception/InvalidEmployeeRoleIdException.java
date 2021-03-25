package com.hardik.flenderson.exception;

public class InvalidEmployeeRoleIdException extends RuntimeException{

	private static final long serialVersionUID = -5275059572259091613L;

	public InvalidEmployeeRoleIdException() {
		super();
	}

	public InvalidEmployeeRoleIdException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidEmployeeRoleIdException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidEmployeeRoleIdException(String message) {
		super(message);
	}

	public InvalidEmployeeRoleIdException(Throwable cause) {
		super(cause);
	}

}
