package com.assignment.comment.app.infra.web;

import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.assignment.comment.app.infra.web.response.RestApiResponseBodyBuilder;
import com.assignment.comment.app.infra.web.response.model.RestApiResponseBody;

public class RestApiController {

	@Autowired
	protected RequestContextResolver contextResolver;

	@Autowired
	protected RestApiResponseBodyBuilder responseBodyBuilder;

	@Autowired
	protected Validator validator;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}

	public <T> ResponseEntity<RestApiResponseBody<T>> success(T data) {
		return ResponseEntity.ok().body(this.responseBodyBuilder.successBody(data));
	}

	public ResponseEntity<RestApiResponseBody<?>> success() {
		return ResponseEntity.ok().body(this.responseBodyBuilder.successBody());
	}

	protected HttpServletRequest request() {
		return this.contextResolver.request();
	}

	protected HttpServletResponse response() {
		return this.contextResolver.response();
	}

	protected TimeZone timeZone() {
		return this.contextResolver.timeZone();
	}
}
