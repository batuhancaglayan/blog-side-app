package com.assignment.comment.app.dao;

import com.assignment.comment.app.model.data.CommentModel;

public interface CommentDao {
	
	public boolean createComment(CommentModel commentModel);
	
	public void softDeleteItem(String commentId);
}
