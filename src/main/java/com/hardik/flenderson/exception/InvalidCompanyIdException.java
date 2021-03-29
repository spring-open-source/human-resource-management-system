package com.hardik.flenderson.exception;

public class InvalidCompanyIdException extends RuntimeException{

	private static final long serialVersionUID = -2475658682503194910L;

	public InvalidCompanyIdException() {
		super();
	}

	public InvalidCompanyIdException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidCompanyIdException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidCompanyIdException(String message) {
		super(message);
	}

	public InvalidCompanyIdException(Throwable cause) {
		super(cause);
	}

}
