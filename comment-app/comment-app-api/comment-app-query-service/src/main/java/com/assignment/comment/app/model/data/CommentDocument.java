package com.assignment.comment.app.model.data;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDocument {

	private String id;
	
	private String email;
	
	private String comment;
	
	private Instant createdAt;
	
	private Instant updatedAt;
	
	private Instant identifiedAt;
	
	private Integer isActive;
}
