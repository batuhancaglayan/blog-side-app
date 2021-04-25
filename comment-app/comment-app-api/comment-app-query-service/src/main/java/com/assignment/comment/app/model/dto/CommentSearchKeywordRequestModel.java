package com.assignment.comment.app.model.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentSearchKeywordRequestModel extends CommentSearchRequestModel {

	@NotNull
	@Size(min = 1, max = 50)
	private String keyword;

}
