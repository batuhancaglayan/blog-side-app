package com.assignment.comment.app.infra.es.exception;

public class ESClientIndexNotFoundException extends ESClientException {

	private static final long serialVersionUID = -2815432273863132985L;
	
	protected static final String CODE = "ES_CLIENT_EXCEPTION_2"; 

	public ESClientIndexNotFoundException(String message, Throwable cause) {
		super(message, cause, CODE);
	}
}
