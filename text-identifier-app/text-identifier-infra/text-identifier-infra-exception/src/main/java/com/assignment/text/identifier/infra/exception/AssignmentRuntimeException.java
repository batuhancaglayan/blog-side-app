package com.assignment.text.identifier.infra.exception;

public class AssignmentRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -5396840209976051515L;

	public AssignmentRuntimeException() {
		super();
	}

	public AssignmentRuntimeException(String message) {
		super(message);
	}

	public AssignmentRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}
}
