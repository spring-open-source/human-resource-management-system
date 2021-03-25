package com.hardik.flenderson.exception;

public class AccessTokenExpiredException extends RuntimeException {

	private static final long serialVersionUID = -7622501156892116382L;

	public AccessTokenExpiredException() {
		super();
	}

	public AccessTokenExpiredException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AccessTokenExpiredException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccessTokenExpiredException(String message) {
		super(message);
	}

	public AccessTokenExpiredException(Throwable cause) {
		super(cause);
	}

}
