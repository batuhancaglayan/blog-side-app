package com.assignment.comment.app.service;

import java.util.List;

import com.assignment.comment.app.model.dto.CommentSearchKeywordRequestModel;
import com.assignment.comment.app.model.dto.CommentSearchRequestModel;
import com.assignment.comment.app.model.dto.CommentSearchResponseModel;

public interface CommentService {

	public List<CommentSearchResponseModel> findByEmail(CommentSearchRequestModel requestModel);

	public List<CommentSearchResponseModel> findByEmailAndSearchInComment(CommentSearchKeywordRequestModel requestModel);
}
