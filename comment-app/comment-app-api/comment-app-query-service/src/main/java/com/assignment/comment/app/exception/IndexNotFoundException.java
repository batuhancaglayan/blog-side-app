package com.assignment.comment.app.exception;

import com.assignment.comment.app.infra.exception.AssignmentRuntimeException;

public class IndexNotFoundException extends AssignmentRuntimeException {

	private static final long serialVersionUID = -2815432273863132985L;
	
	protected static final String CODE = "QUERY_SERVICE_1001"; 

	public IndexNotFoundException(String message, Throwable cause) {
		super(message, cause, CODE);
	}
}
