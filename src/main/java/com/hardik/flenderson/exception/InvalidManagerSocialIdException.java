package com.hardik.flenderson.exception;

public class InvalidManagerSocialIdException extends RuntimeException{

	private static final long serialVersionUID = -8316656744325109076L;

	public InvalidManagerSocialIdException() {
		super();
	}

	public InvalidManagerSocialIdException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidManagerSocialIdException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidManagerSocialIdException(String message) {
		super(message);
	}

	public InvalidManagerSocialIdException(Throwable cause) {
		super(cause);
	}

}
