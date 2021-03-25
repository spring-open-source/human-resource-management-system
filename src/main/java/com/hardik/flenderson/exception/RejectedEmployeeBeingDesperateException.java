package com.hardik.flenderson.exception;

public class RejectedEmployeeBeingDesperateException extends RuntimeException {

	private static final long serialVersionUID = 5430960104690338276L;

	public RejectedEmployeeBeingDesperateException() {
		super();
	}

	public RejectedEmployeeBeingDesperateException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RejectedEmployeeBeingDesperateException(String message, Throwable cause) {
		super(message, cause);
	}

	public RejectedEmployeeBeingDesperateException(String message) {
		super(message);
	}

	public RejectedEmployeeBeingDesperateException(Throwable cause) {
		super(cause);
	}
	
}
