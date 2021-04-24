package com.assignment.comment.app.infra.aws.sqs.exception;

import com.assignment.comment.app.infra.exception.AssignmentRuntimeException;

public class SqsClientException extends AssignmentRuntimeException {

	private static final long serialVersionUID = 995376328930621518L;
	
	protected static final String CODE = "SQS_CLIENT_EXCEPTION_1"; 

	public SqsClientException(String message, Throwable cause) {
		this(message, cause, CODE);
	}
	
	public SqsClientException(String message, Throwable cause, String code) {
		super(message, cause, code);
	}
}
