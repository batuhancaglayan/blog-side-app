package com.assignment.comment.app.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentCreateRequestModel {

	@Email
	@NotNull
	private String email;

	@NotNull
	private String comment;
}
