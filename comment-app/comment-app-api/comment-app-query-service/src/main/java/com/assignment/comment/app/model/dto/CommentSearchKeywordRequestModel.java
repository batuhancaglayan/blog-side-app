package com.assignment.comment.app.model.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentSearchKeywordRequestModel extends CommentSearchRequestModel {

	@NotNull
	private String keyword;
}
