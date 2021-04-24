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

import com.assignment.comment.app.infra.web.RestApiController;
import com.assignment.comment.app.infra.web.response.model.RestApiResponseBody;
import com.assignment.comment.app.model.dto.CommentCreateRequestModel;
import com.assignment.comment.app.model.dto.CommentCreateResponseModel;
import com.assignment.comment.app.service.CommentService;

@RestController
@RequestMapping("/comment")
public class CommentCommandController extends RestApiController {

	private CommentService commentService;

	public CommentCommandController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	@PostMapping
	public ResponseEntity<RestApiResponseBody<CommentCreateResponseModel>> createComment(
			@Valid @RequestBody CommentCreateRequestModel commentCreateRequestModel) {
		
		return this.success(this.commentService.createComment(commentCreateRequestModel));
	}

	@DeleteMapping("/{commentId}")
	public ResponseEntity<RestApiResponseBody<?>> deleteComment(@NotNull @PathVariable String commentId) {
		
		this.commentService.removeComment(commentId);
		return this.success();
	}
}
