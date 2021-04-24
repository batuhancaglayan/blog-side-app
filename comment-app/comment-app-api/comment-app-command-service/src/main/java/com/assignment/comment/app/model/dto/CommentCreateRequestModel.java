package com.assignment.comment.app.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentCreateRequestModel {

	@Email
	@NotNull
	private String email;

	@NotNull
	@Size(min = 1, max = 20000)
	private String comment;
}
