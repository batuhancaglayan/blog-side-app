package com.assignment.comment.app.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentSearchResponseModel {

	private String id;
	
	private String email;
	
	private String comment;
}
