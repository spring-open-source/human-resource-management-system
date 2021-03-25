package com.hardik.flenderson.exception;

public class KeycloakCodeExchangeFailureException extends RuntimeException {

	private static final long serialVersionUID = 1473347590343443221L;

	public KeycloakCodeExchangeFailureException() {
		super();
	}

	public KeycloakCodeExchangeFailureException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public KeycloakCodeExchangeFailureException(String message, Throwable cause) {
		super(message, cause);
	}

	public KeycloakCodeExchangeFailureException(String message) {
		super(message);
	}

	public KeycloakCodeExchangeFailureException(Throwable cause) {
		super(cause);
	}

}
