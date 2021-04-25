package com.assignment.comment.app.exception;

import com.assignment.comment.app.infra.exception.AssignmentRuntimeException;

public class CommentItemNotFoundException extends AssignmentRuntimeException {

	private static final long serialVersionUID = 538936813164530463L;

	protected static final String CODE = "COMMAND_SERVICE_1001"; 
	
	public CommentItemNotFoundException(String message) {
		super(message, null, CODE);
	}
}
