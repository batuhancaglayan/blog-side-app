package com.assignment.comment.app.infra.web.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.assignment.comment.app.infra.web.RequestContextResolver;
import com.assignment.comment.app.infra.web.response.model.RestApiError;
import com.assignment.comment.app.infra.web.response.model.RestApiResponseBody;

@Component

public class RestApiResponseBodyBuilder {

	@Autowired
	private RequestContextResolver requestContextResolver;

	public RestApiResponseBody<String> successBody() {
		
		return new RestApiResponseBody<>(this.requestContextResolver.clientTime(), "success", null, null);
	}

	public <T> RestApiResponseBody<T> successBody(T data) {
		
		return new RestApiResponseBody<>(this.requestContextResolver.clientTime(), "success", data, null);
	}

	public <T> RestApiResponseBody<T> errorBody(String errorMessage) {
		
		return (RestApiResponseBody<T>) RestApiResponseBody.builder().data(null)
				.timestamp(this.requestContextResolver.clientTime())
				.errors(new RestApiError[] {
						RestApiError.builder().type(RestApiError.DEFAULT).message(errorMessage).build() })
				.message("error").build();
	}

	public RestApiResponseBody<Object> errorBody(BindingResult validationResult) {
		
		return RestApiResponseBody.builder().data(null).timestamp(this.requestContextResolver.clientTime())
				.message("error").errors(this.validationErrors(validationResult)).build();
	}

	public RestApiResponseBody<Object> errorBody(MethodArgumentTypeMismatchException exception) {
		
		return RestApiResponseBody.builder().data(null).timestamp(this.requestContextResolver.clientTime())
				.message("error")
				.errors(new RestApiError[] { RestApiError.builder().type(RestApiError.DEFAULT)
						.title(exception.getParameter().getParameterName()).message(exception.getMessage()).build() })
				.build();
	}

	public RestApiResponseBody<Object> errorBody(MissingServletRequestParameterException exception) {
		
		return RestApiResponseBody.builder().data(null).timestamp(this.requestContextResolver.clientTime())
				.message("error")
				.errors(new RestApiError[] { RestApiError.builder().type(RestApiError.DEFAULT)
						.title(exception.getParameterName()).message(exception.getMessage()).build() })
				.build();
	}

	protected RestApiError[] validationErrors(BindingResult validationResult) {
		List<RestApiError> result = new ArrayList<>();

		for (ObjectError objectError : validationResult.getGlobalErrors()) {
			RestApiError error = RestApiError.builder().title(objectError.getObjectName())
					.message(objectError.getObjectName() + " " + objectError.getDefaultMessage())
					.type(RestApiError.OBJECT).build();
			result.add(error);
		}

		for (FieldError fieldError : validationResult.getFieldErrors()) {
			RestApiError error = RestApiError.builder().title(fieldError.getField())
					.message(fieldError.getField() + " " + fieldError.getDefaultMessage()).type(RestApiError.FIELD)
					.build();
			result.add(error);
		}

		return result.toArray(new RestApiError[result.size()]);
	}
}
