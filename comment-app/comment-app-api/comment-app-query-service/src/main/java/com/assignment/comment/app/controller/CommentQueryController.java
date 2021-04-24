package com.assignment.comment.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.comment.app.infra.web.RestApiController;
import com.assignment.comment.app.infra.web.response.model.RestApiResponseBody;
import com.assignment.comment.app.model.dto.CommentSearchKeywordRequestModel;
import com.assignment.comment.app.model.dto.CommentSearchRequestModel;
import com.assignment.comment.app.model.dto.CommentSearchResponseModel;
import com.assignment.comment.app.service.CommentService;

@RestController
@RequestMapping("comment")
public class CommentQueryController extends RestApiController {

	private CommentService commentService;

	public CommentQueryController(CommentService commentService) {
		this.commentService = commentService;
	}

	@PostMapping("/email")
	public ResponseEntity<RestApiResponseBody<List<CommentSearchResponseModel>>> searchByEmail(
			@Valid @RequestBody CommentSearchRequestModel requestModel) {

		return this.success(this.commentService.findByEmail(requestModel));
	}

	@PostMapping("/email-keyword")
	public  ResponseEntity<RestApiResponseBody<List<CommentSearchResponseModel>>>  searchByEmailAndKeyword(
			@Valid @RequestBody CommentSearchKeywordRequestModel requestModel) {

		return this.success(this.commentService.findByEmailAndSearchInComment(requestModel));
	}
}
