package com.hardik.flenderson.exception;

public class InvalidSupportTicketIdException extends RuntimeException{

	private static final long serialVersionUID = 7646902673097242667L;

	public InvalidSupportTicketIdException() {
		super();
	}

	public InvalidSupportTicketIdException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidSupportTicketIdException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidSupportTicketIdException(String message) {
		super(message);
	}

	public InvalidSupportTicketIdException(Throwable cause) {
		super(cause);
	}

}
