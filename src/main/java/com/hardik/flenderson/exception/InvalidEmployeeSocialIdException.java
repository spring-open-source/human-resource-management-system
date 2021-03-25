package com.hardik.flenderson.exception;

public class InvalidEmployeeSocialIdException extends RuntimeException{

	private static final long serialVersionUID = 6224096285158422587L;

	public InvalidEmployeeSocialIdException() {
		super();
	}

	public InvalidEmployeeSocialIdException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidEmployeeSocialIdException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidEmployeeSocialIdException(String message) {
		super(message);
	}

	public InvalidEmployeeSocialIdException(Throwable cause) {
		super(cause);
	}

}
