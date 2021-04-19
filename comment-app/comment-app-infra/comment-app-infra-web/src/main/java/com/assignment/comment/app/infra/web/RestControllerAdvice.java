package com.assignment.comment.app.infra.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.assignment.comment.app.infra.exception.AssignmentRuntimeException;

@org.springframework.web.bind.annotation.RestControllerAdvice
public class RestControllerAdvice {

	@ExceptionHandler(AssignmentRuntimeException.class)
	protected ResponseEntity<Object> handleConflict(AssignmentRuntimeException ex, WebRequest request) {

		// todo: handle and create error response
		return null;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleConflict(Exception ex, WebRequest request) {
		
		// todo: handle and create error response
		return null;
	}
}
