package com.assignment.comment.app.service;

import com.assignment.comment.app.model.dto.CommentCreateRequestModel;
import com.assignment.comment.app.model.dto.CommentCreateResponseModel;

public interface CommentService {

	public CommentCreateResponseModel createComment(CommentCreateRequestModel commentCreateRequestModel);
	
	public void removeComment(String commentId);
}
