package com.hardik.flenderson.exception;

public class CorrectScopeNotSpecifiedException extends RuntimeException {

	private static final long serialVersionUID = 4384328814675947838L;

	public CorrectScopeNotSpecifiedException() {
		super();
	}

	public CorrectScopeNotSpecifiedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CorrectScopeNotSpecifiedException(String message, Throwable cause) {
		super(message, cause);
	}

	public CorrectScopeNotSpecifiedException(String message) {
		super(message);
	}

	public CorrectScopeNotSpecifiedException(Throwable cause) {
		super(cause);
	}

}
