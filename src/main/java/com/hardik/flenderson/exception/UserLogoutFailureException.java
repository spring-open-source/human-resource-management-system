package com.hardik.flenderson.exception;

public class UserLogoutFailureException extends RuntimeException {

	private static final long serialVersionUID = -6042479910268224336L;

	public UserLogoutFailureException() {
		super();
	}

	public UserLogoutFailureException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UserLogoutFailureException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserLogoutFailureException(String message) {
		super(message);
	}

	public UserLogoutFailureException(Throwable cause) {
		super(cause);
	}
	
}
