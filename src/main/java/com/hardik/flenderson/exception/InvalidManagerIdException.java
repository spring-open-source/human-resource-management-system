package com.hardik.flenderson.exception;

public class InvalidManagerIdException extends RuntimeException {

	private static final long serialVersionUID = -5796269447751626062L;

	public InvalidManagerIdException() {
		super();
	}

	public InvalidManagerIdException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidManagerIdException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidManagerIdException(String message) {
		super(message);
	}

	public InvalidManagerIdException(Throwable cause) {
		super(cause);
	}

}
