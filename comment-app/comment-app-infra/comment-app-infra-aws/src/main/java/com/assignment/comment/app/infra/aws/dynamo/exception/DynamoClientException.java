package com.assignment.comment.app.infra.aws.dynamo.exception;

import com.assignment.comment.app.infra.exception.AssignmentRuntimeException;

public class DynamoClientException extends AssignmentRuntimeException {

	private static final long serialVersionUID = -7868988308866164926L;
	
	protected static final String CODE = "DYNAMO_CLIENT_EXCEPTION_1"; 
	
	public DynamoClientException(String message) {
		this(message, null, CODE);
	}

	public DynamoClientException(String message, Throwable cause) {
		this(message, cause, CODE);
	}
	
	public DynamoClientException(String message, Throwable cause, String code) {
		super(message, cause, code);
	}
}
