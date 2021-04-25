package com.assignment.comment.app.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentSearchRequestModel {

	@Email
	@NotNull
	private String email;
	
	@Min(0)
	private int from = 0;
	
	@Min(1)
	@Max(100)
	private int size;
}
