package com.hardik.flenderson.exception;

public class RefreshAccessTokenFailureException extends RuntimeException {

	private static final long serialVersionUID = -3527100326763389901L;

	public RefreshAccessTokenFailureException() {
		super();
	}

	public RefreshAccessTokenFailureException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RefreshAccessTokenFailureException(String message, Throwable cause) {
		super(message, cause);
	}

	public RefreshAccessTokenFailureException(String message) {
		super(message);
	}

	public RefreshAccessTokenFailureException(Throwable cause) {
		super(cause);
	}
}
