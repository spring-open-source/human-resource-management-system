package com.hardik.flenderson.exception;

public class AuthenticationBearerStrategyNotUsedException extends RuntimeException {

	private static final long serialVersionUID = 3718232587120961217L;

	public AuthenticationBearerStrategyNotUsedException() {
		super();
	}

	public AuthenticationBearerStrategyNotUsedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AuthenticationBearerStrategyNotUsedException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthenticationBearerStrategyNotUsedException(String message) {
		super(message);
	}

	public AuthenticationBearerStrategyNotUsedException(Throwable cause) {
		super(cause);
	}

}
