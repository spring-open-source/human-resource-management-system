package com.hardik.flenderson.exception;

public class EmptyManagerProfilePictureException extends RuntimeException {

	private static final long serialVersionUID = 5469229415583035610L;

	public EmptyManagerProfilePictureException() {
		super();
	}

	public EmptyManagerProfilePictureException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EmptyManagerProfilePictureException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmptyManagerProfilePictureException(String message) {
		super(message);
	}

	public EmptyManagerProfilePictureException(Throwable cause) {
		super(cause);
	}
	
}
