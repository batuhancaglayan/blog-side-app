package com.assignment.text.identifier.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TextIdentificationReqeustModel {

	@Email
	@NotNull
	private String email;

	@NotNull
	private String comment;
}
