package com.assignment.comment.app.infra.aws.dynamo.exception;

public class DynamoItemNotFoundException extends DynamoClientException {

	private static final long serialVersionUID = 538936813164530463L;

	protected static final String CODE = "DYNAMO_CLIENT_EXCEPTION_2"; 
	
	public DynamoItemNotFoundException(String message) {
		super(message, null, CODE);
	}
}
