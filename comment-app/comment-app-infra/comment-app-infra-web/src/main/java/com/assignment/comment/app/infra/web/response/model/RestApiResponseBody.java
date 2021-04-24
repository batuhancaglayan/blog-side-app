package com.assignment.comment.app.infra.web.response.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestApiResponseBody<T> {

	private LocalDateTime timestamp;
	
	private String message;
	
	private T data;
	
	private RestApiError[] errors;
}
