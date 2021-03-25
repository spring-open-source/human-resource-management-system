package com.hardik.flenderson.exception;

public class InvalidMasterRoleIdException extends RuntimeException{

	private static final long serialVersionUID = -5068182691047841534L;

	public InvalidMasterRoleIdException() {
		super();
	}

	public InvalidMasterRoleIdException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidMasterRoleIdException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidMasterRoleIdException(String message) {
		super(message);
	}

	public InvalidMasterRoleIdException(Throwable cause) {
		super(cause);
	}

}
