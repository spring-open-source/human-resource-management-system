package com.hardik.flenderson.exception;

public class CompanyJoinInvitationAlreadySentException extends RuntimeException {

	private static final long serialVersionUID = -3690176545077983746L;

	public CompanyJoinInvitationAlreadySentException() {
		super();
	}

	public CompanyJoinInvitationAlreadySentException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CompanyJoinInvitationAlreadySentException(String message, Throwable cause) {
		super(message, cause);
	}

	public CompanyJoinInvitationAlreadySentException(String message) {
		super(message);
	}

	public CompanyJoinInvitationAlreadySentException(Throwable cause) {
		super(cause);
	}
	
}
