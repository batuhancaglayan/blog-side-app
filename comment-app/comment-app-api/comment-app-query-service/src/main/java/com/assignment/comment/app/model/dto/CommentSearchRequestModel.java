package com.assignment.comment.app.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentSearchRequestModel {

	@Email
	@NotNull
	private String email;
}
