package com.hardik.flenderson.exception;

public class MissingAccessTokenAndUserIdException extends RuntimeException {

	private static final long serialVersionUID = 5664621792914112394L;

	public MissingAccessTokenAndUserIdException() {
		super();
	}

	public MissingAccessTokenAndUserIdException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MissingAccessTokenAndUserIdException(String message, Throwable cause) {
		super(message, cause);
	}

	public MissingAccessTokenAndUserIdException(String message) {
		super(message);
	}

	public MissingAccessTokenAndUserIdException(Throwable cause) {
		super(cause);
	}
}
