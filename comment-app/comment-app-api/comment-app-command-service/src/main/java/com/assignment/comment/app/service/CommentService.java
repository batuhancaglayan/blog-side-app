package com.assignment.comment.app.service;

import com.assignment.comment.app.model.CommentCreateRequestModel;
import com.assignment.comment.app.model.CommentCreateResponseModel;

public interface CommentService {

	public CommentCreateResponseModel createComment(CommentCreateRequestModel commentCreateRequestModel);
	
	public void removeComment(String commentId);
}
