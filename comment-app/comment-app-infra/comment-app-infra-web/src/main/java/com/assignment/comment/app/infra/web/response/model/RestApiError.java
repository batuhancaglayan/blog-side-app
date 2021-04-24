package com.assignment.comment.app.infra.web.response.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RestApiError {

	private String title;
	
	private String message;
	
	private String type;

	/**
	 * Error Types
	 */
	public static final String DEFAULT = "default-error";
	
	public static final String OBJECT = "object-error";
	
	public static final String FIELD = "field-error";
}
