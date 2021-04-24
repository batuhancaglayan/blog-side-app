package com.assignment.comment.app.infra.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.assignment.comment.app.infra.exception.AssignmentRuntimeException;
import com.assignment.comment.app.infra.web.message.MessageResolver;
import com.assignment.comment.app.infra.web.response.RestApiResponseBodyBuilder;
import com.assignment.comment.app.infra.web.response.model.RestApiResponseBody;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@org.springframework.web.bind.annotation.RestControllerAdvice
public class RestControllerAdvice {

	@Autowired
	private MessageResolver ressageResolver;

	@Autowired
	private RestApiResponseBodyBuilder responseBodyBuilder;

	@ExceptionHandler(AssignmentRuntimeException.class)
	protected ResponseEntity<Object> assignmentException(AssignmentRuntimeException exception, WebRequest request) {

		log.error(exception.getMessage(), exception);
		String message =  ressageResolver.getMessageText(exception.getCode());
		RestApiResponseBody<Object> responseBody = this.responseBodyBuilder.errorBody(message);
		return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> invalidArgument(MethodArgumentNotValidException exception) {

		log.error(exception.getMessage(), exception);
		RestApiResponseBody<Object> responseBody = this.responseBodyBuilder.errorBody(exception.getBindingResult());
		return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<?> argumentTypeMismatch(MethodArgumentTypeMismatchException exception) {

		log.error(exception.getMessage(), exception);
		RestApiResponseBody<Object> responseBody = this.responseBodyBuilder.errorBody(exception);
		return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<?> missingParameter(MissingServletRequestParameterException exception) {

		log.error(exception.getMessage(), exception);
		RestApiResponseBody<Object> responseBody = this.responseBodyBuilder.errorBody(exception);
		return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
	}

	// TODO: ADD more specific exception types and their handler logics.
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> unknownException(Exception exception) {

		log.error(exception.getMessage(), exception);
		RestApiResponseBody<Object> responseBody = this.responseBodyBuilder
				.errorBody(MessageResolver.UNKNOWN_EXCEPTION_TEXT);
		return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
	}
}
