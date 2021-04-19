package com.assignment.comment.app.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.comment.app.model.CommentCreateRequestModel;
import com.assignment.comment.app.model.CommentCreateResponseModel;
import com.assignment.comment.app.service.CommentService;

@RestController
@RequestMapping("comment")
public class CommentController {

	private CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	@PostMapping
	public ResponseEntity<CommentCreateResponseModel> createComment(
			@Valid @RequestBody CommentCreateRequestModel commentCreateRequestModel) {
		return ResponseEntity.ok()
				.body(this.commentService.createComment(commentCreateRequestModel));
	}

	@DeleteMapping("/{commentId}")
	public ResponseEntity<Void> createTextIdentification(@NotNull @PathVariable String commentId) {
		this.commentService.removeComment(commentId);
		return ResponseEntity.ok().build();
	}
}
