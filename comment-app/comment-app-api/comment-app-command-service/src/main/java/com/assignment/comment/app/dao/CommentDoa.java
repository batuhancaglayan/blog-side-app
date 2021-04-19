package com.assignment.comment.app.dao;

import com.assignment.comment.app.model.CommentModel;

public interface CommentDoa {
	
	public boolean createComment(CommentModel commentModel);
	
	public void softDeleteItem(String commentId);
}
