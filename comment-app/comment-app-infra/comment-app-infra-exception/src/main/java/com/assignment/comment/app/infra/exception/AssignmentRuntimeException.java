package com.assignment.comment.app.infra.exception;

import lombok.Getter;

@Getter
public class AssignmentRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -5396840209976051515L;

	// combination of project domain and error number like A_Service_1
	private String code = "GENERIC_EXCEPTION_1";
	
	public AssignmentRuntimeException() {
		super();
	}

	public AssignmentRuntimeException(String message) {
		super(message);
	}

	public AssignmentRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public AssignmentRuntimeException(String message, Throwable cause, String code) {
		super(message, cause);
		this.code = code;
	}
}
