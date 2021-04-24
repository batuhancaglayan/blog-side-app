package com.assignment.comment.app.infra.es.exception;

import com.assignment.comment.app.infra.exception.AssignmentRuntimeException;

public class ESClientException extends AssignmentRuntimeException {

	private static final long serialVersionUID = -213096632130289469L;
	
	protected static final String CODE = "ES_CLIENT_EXCEPTION_1"; 

	public ESClientException(String message, Throwable cause) {
		this(message, cause, CODE);
	}
	
	public ESClientException(String message, Throwable cause, String code) {
		super(message, cause, code);
	}
}
