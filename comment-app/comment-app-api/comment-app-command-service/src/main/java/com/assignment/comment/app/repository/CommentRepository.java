package com.assignment.comment.app.repository;

import com.assignment.comment.app.model.data.CommentModel;

public interface CommentRepository {

	public boolean createComment(CommentModel commentModel);
	
	public void softDeleteItem(String commentId);
}
