package com.assignment.comment.app.model.data;

import java.time.Instant;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentModel {

	private String id = UUID.randomUUID().toString();

	private Long createdAt = Instant.now().getEpochSecond();

	private String email;

	private String comment;
	
	public CommentModel(String email, String comment) {
		this.email = email;
		this.comment= comment;
	}
}
