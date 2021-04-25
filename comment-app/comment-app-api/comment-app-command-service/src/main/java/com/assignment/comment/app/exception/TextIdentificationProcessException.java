package com.assignment.comment.app.exception;

import com.assignment.comment.app.infra.exception.AssignmentRuntimeException;

public class TextIdentificationProcessException extends AssignmentRuntimeException {

	private static final long serialVersionUID = 538936813164530463L;

	protected static final String CODE = "COMMAND_SERVICE_1002"; 
	
	public TextIdentificationProcessException(String message, Throwable cause) {
		super(message, cause, CODE);
	}
}