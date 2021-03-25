package com.hardik.flenderson.exception;

public class ManagerSocialWithNameAlreadyExistsException extends RuntimeException{

	private static final long serialVersionUID = 6977829402654395559L;

	public ManagerSocialWithNameAlreadyExistsException() {
		super();
	}

	public ManagerSocialWithNameAlreadyExistsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ManagerSocialWithNameAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public ManagerSocialWithNameAlreadyExistsException(String message) {
		super(message);
	}

	public ManagerSocialWithNameAlreadyExistsException(Throwable cause) {
		super(cause);
	}

}
