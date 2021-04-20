package com.assignment.comment.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.comment.app.model.dto.CommentSearchKeywordRequestModel;
import com.assignment.comment.app.model.dto.CommentSearchRequestModel;
import com.assignment.comment.app.model.dto.CommentSearchResponseModel;
import com.assignment.comment.app.service.CommentService;

@RestController
@RequestMapping("comment")
public class CommentQueryController {

	private CommentService commentService;

	public CommentQueryController(CommentService commentService) {
		this.commentService = commentService;
	}

	@PostMapping("query/email")
	public ResponseEntity<List<CommentSearchResponseModel>> searchByEmail(
			@Valid @RequestBody CommentSearchRequestModel requestModel) {

		return ResponseEntity.ok().body(this.commentService.findByEmail(requestModel));
	}

	@PostMapping("query/email-keyword")
	public ResponseEntity<List<CommentSearchResponseModel>> searchByEmailAndKeyword(
			@Valid @RequestBody CommentSearchKeywordRequestModel requestModel) {

		return ResponseEntity.ok().body(this.commentService.findByEmailAndSearchInComment(requestModel));
	}
}
